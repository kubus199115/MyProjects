import sys
import random
 
import pygame
from pygame.locals import *
from matplotlib import pyplot as plt
import numpy as np


class mover:
  def __init__(self, particle):
    self.particle = particle

  def move(self):
    self.particle.pos = self.particle.pos + (self.particle.vel * dt)
    

  def draw(self):
    pygame.draw.circle(screen, (255,255,0), self.particle.get_pos(), 10)


class forces:
  @staticmethod
  def zero_force():
    return np.array([0,0])
  
  # grawitacja (stała blisko ziemi)
  @staticmethod
  def constant_gravity(m, g):
    return np.array([0, m*g])
  
  # siła lepkości (oporu)
  @staticmethod
  def linearDrag(k, v):
    if np.linalg.norm(v) > 0:
      return v * -k
    else:
      return np.array([0,0])
    
  # siła grawitacji
  @staticmethod
  def gravity(G, m1, m2, r):
    return r * (-G*m1*m2 / (np.linalg.norm(r)**3))
    
  # suma sił
  @staticmethod
  def add_forces(forces):
    force_sum = np.array([0,0])
    for f in forces:
      force_sum = force_sum + f
    return force_sum


class forcer(mover):
  def __init__(self, particle):
    self.force = np.array([0,0])
    self.acc = np.array([0,0])
    super().__init__(particle)

  def move(self):
    super().move()
    self.calc_force()
    self.update_acc()
    self.update_velo()


  def calc_force(self):
    self.force = forces.zero_force()

  def update_acc(self):
    self.acc = self.force * (1/self.particle.get_mass())

  def update_velo(self):
    self.particle.vel = self.particle.vel + (self.acc*dt)


class ramp(forcer):
  def __init__(self, particle, angle):
    self.g = 10
    self.ck = 0.2
    self.cs = 0.25
    self.vtol = 0.000001
    self.angle = angle
    super().__init__(particle)

  # oblicza siłę normalną, z jaką ciało naciska na pochylnie
  def N_force(self):
    return self.particle.get_mass() * self.g * np.cos(self.angle)
  
  # oblicza siłę, z jaką ciało zsuwa się z pochylni i zwraca jej wektor
  def ramp_force(self):
    rf = self.particle.get_mass() * self.g * np.sin(self.angle)
    return self.ramp_vector(rf)

  # oblicza siłę tarcia i zwraca jej wektor
  def friction_force(self):
    ff = 0
    csf = self.cs * self.N_force()  # statyczne tarcie
    ckf = self.ck * self.N_force()  # kinetyczne tarcie
    # jeśli statyczne
    if np.linalg.norm(self.particle.vel) < self.vtol:
      ff = np.min([csf, self.particle.get_mass() * self.g * np.sin(self.angle)])
    # jeśli kinetyczne
    else:
      ff = ckf    
    return -self.ramp_vector(ff)

  # zwraca wektor wzdłuż pochylni
  def ramp_vector(self, mag):
    _x = mag * np.cos(self.angle)
    _y = mag * np.sin(self.angle)
    return np.array([_x, _y])

  def calc_force(self):
    rf = self.ramp_force()
    ff = self.friction_force()
    self.force = forces.add_forces([rf, ff])


class particle:
  def __init__(self, pos, vel=[0,0], mass=1):
    self.pos = np.array(pos)
    self.vel = np.array(vel)
    self.mass = mass

  def set_pos(self, pos):
    self.pos = np.array(pos)

  def get_pos(self):
    return self.pos
  
  def set_vel(self, vel):
    self.vel = np.array(vel)

  def get_vel(self):
    return self.vel
  
  def set_mass(self, mass):
    self.mass = mass

  def get_mass(self):
    return self.mass

 
pygame.init()

fps = 60
dt = 1/60
fpsClock = pygame.time.Clock()
 
width, height = 800, 600
screen = pygame.display.set_mode((width, height))

topx = 20
topy = 225
botx = width
boty = height
angle = np.pi/2 - np.arctan2(botx-topx, boty-topy)
print(np.rad2deg(angle))
print(np.tan(angle))

ball = ramp(particle([30,topy-10]), angle)

# Game loop.
while True:
  screen.fill((0, 0, 0))
  
  for event in pygame.event.get():
    if event.type == QUIT:
      pygame.quit()
      sys.exit()

  # Update.
  ball.move()
    
  # Draw.
  pygame.draw.line(screen, (255,0,255), (topx, topy), (botx, boty))
  ball.draw()
  
  pygame.display.flip()
  fpsClock.tick(fps)