#ifndef _ELEMENT_H
#define _ELEMENT_H

#include "SDL/SDL.h"

#include "Stale.h"

class Element
{
    public:
        SDL_Rect Pozycja;
        SDL_Rect Pozycja_k;
        SDL_Rect Pozycja_p;

        int typ;

        bool ruch_prawo;
        bool ruch_gora;

    public:
        Element();

        int Zwroc_typ();
};

#endif // _ELEMENT_H
