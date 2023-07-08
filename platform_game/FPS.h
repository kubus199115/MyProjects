#ifndef _FPS_H
#define _FPS_H

#include "SDL/SDL.h"

class FPS
{
private:
    int CzasStart;

public:
    FPS();

    void Startuj();

    int PobierzCzas();
};

#endif // _FPS_H
