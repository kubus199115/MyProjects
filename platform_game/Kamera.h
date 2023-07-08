#ifndef _KAMERA_H
#define _KAMERA_H

#include "SDL/SDL.h"
#include "Stale.h"

class Kamera
{
public:
    static Kamera Kontrola_Kamery;
    SDL_Rect Pozycja;

public:
    Kamera();
    void Ustaw_Kamere(int x, int y);
};
#endif // _KAMERA_H
