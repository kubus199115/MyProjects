#ifndef _ZDARZENIE_H
#define _ZDARZENIE_H

#include "SDL/SDL.h"

class Zdarzenie
{
public:
    Zdarzenie();

    virtual void Obsluga_Zdarzen(SDL_Event *zdarzenie);
    virtual void Wcisnij_klawisz(SDLKey sym, SDLMod mod, Uint16 unicode);
    virtual void Pusc_klawisz(SDLKey sym, SDLMod mod, Uint16 unicode);
    virtual void Wyjscie();
};

#endif // _ZDARZENIE_H
