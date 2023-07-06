import sys
import random
 
import pygame
from pygame.locals import *


class multi_mover:
  def __init__(self, particles):
    self.particles = particles

  def move(self):
    for particle in self.particles:
      particle.vel[0] += particle.acc[0] * dt
      particle.vel[1] += particle.acc[1] * dt
      particle.pos[0] += particle.vel[0] * dt
      particle.pos[1] += particle.vel[1] * dt

  def draw(self):
    for particle in self.particles:
      pygame.draw.circle(screen, (255,255,0), particle.get_pos(), 20)


class mover:
  def __init__(self, particle):
    self.particle = particle

  def move(self):
    self.particle.vel[0] += self.particle.acc[0] * dt
    self.particle.vel[1] += self.particle.acc[1] * dt
    self.particle.pos[0] += self.particle.vel[0] * dt
    self.particle.pos[1] += self.particle.vel[1] * dt

  def draw(self):
    pygame.draw.circle(screen, (255,255,0), self.particle.get_pos(), 20)


class particle:
  def __init__(self, pos, vel, acc=[0, 9.8]):
    self.pos = pos
    self.vel = vel
    self.acc = acc

  def set_pos(self, pos):
    self.pos = pos

  def get_pos(self):
    return self.pos
  
  def set_vel(self, vel):
    self.vel = vel

  def get_vel(self):
    return self.vel
  
  def set_acc(self, acc):
    self.acc = acc

  def get_acc(self):
    return self.acc
 
balls = []
for i in range(10):
  balls.append(particle([random.randint(1,640), random.randint(1,480)],
                        [random.randint(-100,100), random.randint(-100,100)]))

mover_obj = multi_mover(balls)

pygame.init()
 
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
  mover_obj.move()
    
  # Draw.
  mover_obj.draw()
  
  pygame.display.flip()
  fpsClock.tick(fps)