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


class orbiter(forcer):
  def __init__(self, orbiting_particle, attractor):
    self.orbiting_particle = orbiting_particle
    self.mass_attractor = attractor.get_mass()
    self.mass_orbiter = orbiting_particle.get_mass()
    self.center = attractor.pos
    self.G = 1
    super().__init__(orbiting_particle)

  def calc_force(self):
    self.force = forces.gravity(self.G, self.mass_attractor, self.mass_orbiter, self.orbiting_particle.pos - self.center)


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

planets = orbiter(particle([200,50], [70, 0]), particle([275, 200], mass=1_000_000))
 
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
  planets.move()
    
  # Draw.
  planets.draw()
  pygame.draw.circle(screen, (255,255,0), [275, 200], 5)
  
  pygame.display.flip()
  fpsClock.tick(fps)