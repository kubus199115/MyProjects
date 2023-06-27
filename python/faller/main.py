import sys
import random
 
import pygame
from pygame.locals import *
from matplotlib import pyplot as plt


class mover:
  def __init__(self, particle):
    self.particle = particle

  def move(self):
    self.particle.pos[0] += self.particle.vel[0] * dt
    self.particle.pos[1] += self.particle.vel[1] * dt

  def draw(self):
    pygame.draw.circle(screen, (255,255,0), self.particle.get_pos(), 10)


class faller(mover):
  def __init__(self, particle):
    self.particle = particle
    self.force = [0,0]
    self.acc = [0,0]
    self.g = 10
    self.k = 0.5
    super().__init__(particle)

  def move(self):
    super().move()
    self.calc_force()
    self.update_acc()
    self.update_velo()


  def calc_force(self):
    self.force[0] = 0
    self.force[1] = self.particle.get_mass() * self.g - self.k * self.particle.vel[1]

  def update_acc(self):
    self.acc[0] = self.force[0] / self.particle.get_mass()
    self.acc[1] = self.force[1] / self.particle.get_mass()

  def update_velo(self):
    self.particle.vel[0] += self.acc[0] * dt
    self.particle.vel[1] += self.acc[1] * dt


class particle:
  def __init__(self, pos, vel=[0,0], mass=1):
    self.pos = pos
    self.vel = vel
    self.mass = mass

  def set_pos(self, pos):
    self.pos = pos

  def get_pos(self):
    return self.pos
  
  def set_vel(self, vel):
    self.vel = vel

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

ball = faller(particle([100,100]))
 
fps = 60
dt = 1/60
fpsClock = pygame.time.Clock()
 
width, height = 640, 480
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