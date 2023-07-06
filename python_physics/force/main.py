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
    
  # suma sił
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


class testForcer(forcer):
  def __init__(self, particle):
    self.g = 10
    self.k = 0.1
    super().__init__(particle)

  def calc_force(self):
    gravity = forces.constant_gravity(self.particle.get_mass(), self.g)
    drag = forces.linearDrag(self.k, self.particle.vel)
    self.force = forces.add_forces([gravity, drag])


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
  

def show_graph(x, y):
  plt.plot(x, y)
  plt.show()

 
pygame.init()

ball = testForcer(particle([50,500], [100, -100]))
 
fps = 60
dt = 1/60
fpsClock = pygame.time.Clock()
 
width, height = 800, 600
screen = pygame.display.set_mode((width, height))

# for graph
x_v = []
y_v = []
i = 0
seconds = 10

# Game loop.
while True:
  screen.fill((0, 0, 0))
  
  for event in pygame.event.get():
    if event.type == QUIT:
      pygame.quit()
      sys.exit()

  # for graph
  time = fps * seconds
  if i<time:
    x_v.append(dt*i)
    y_v.append(ball.particle.vel[1])
  else:
    break
  i=i+1
  
  # Update.
  ball.move()
    
  # Draw.
  ball.draw()
  
  pygame.display.flip()
  fpsClock.tick(fps)

show_graph(x_v, y_v)