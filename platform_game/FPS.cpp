#include "FPS.h"

FPS::FPS()
{
    CzasStart = 0;  //trzyma wystartowany czas
}

void FPS::Startuj()
{
   CzasStart = SDL_GetTicks();
}

int FPS::PobierzCzas()
{
    return SDL_GetTicks() - CzasStart;
}
