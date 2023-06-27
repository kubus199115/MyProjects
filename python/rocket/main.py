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
  
  # grawitacja
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


class rocketer(forcer):
  def __init__(self, rocket, attractor):
    self.rocket = rocket
    self.mass_attractor = attractor.get_mass()
    self.mass_rocket = rocket.get_mass()
    self.center = attractor.pos
    self.dmdt = 0.8
    self.fuel_mass = 3
    self.fuel_used = 0
    self.ve = np.array([0,300])
    self.G = 0.1
    super().__init__(rocket)

  def update_mass(self):
    if self.fuel_used < self.fuel_mass:
      self.fuel_used = self.fuel_used + self.dmdt * dt
      self.rocket.mass = self.rocket.mass - self.dmdt * dt

  def calc_force(self):
    gravity = forces.gravity(self.G, self.mass_attractor, self.mass_rocket, self.rocket.pos - self.center)
    thrust = np.array([0,0])
    if self.fuel_used < self.fuel_mass:
       thrust = self.ve * -self.dmdt
    self.force = forces.add_forces([gravity, thrust])

  def move(self):
    super().move()
    self.update_mass()


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

rocket = rocketer(particle([400,290], mass=10), particle([400, 400], mass=1_000_000))
 
fps = 60
dt = 1/60
fpsClock = pygame.time.Clock()
 
width, height = 640, 480
screen = pygame.display.set_mode((width, height))

# Game loop.
while True:
  screen.fill((0, 0, 0))
  
  for event in pygame.event.get():
    if event.type == QUIT:
      pygame.quit()
      sys.exit()

  # Update.
  rocket.move()
    
  # Draw.
  rocket.draw()
  pygame.draw.circle(screen, (255,255,0), [400, 400], 100)
  pygame.draw.circle(screen, (255,0,0), [400, 400], 5)
    
  pygame.display.flip()
  fpsClock.tick(fps)