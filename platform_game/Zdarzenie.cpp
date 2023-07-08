#include "Zdarzenie.h"

Zdarzenie::Zdarzenie()
{

}

void Zdarzenie::Obsluga_Zdarzen(SDL_Event *zdarzenie)
{
    switch(zdarzenie->type)
    {
        case SDL_KEYDOWN:
        {
            Wcisnij_klawisz(zdarzenie->key.keysym.sym,zdarzenie->key.keysym.mod,zdarzenie->key.keysym.unicode);
            break;
        }
        case SDL_KEYUP:
        {
            Pusc_klawisz(zdarzenie->key.keysym.sym,zdarzenie->key.keysym.mod,zdarzenie->key.keysym.unicode);
            break;
        }
        case SDL_QUIT:
        {
            Wyjscie();
            break;
        }
    }
}

void Zdarzenie::Wcisnij_klawisz(SDLKey sym, SDLMod mod, Uint16 unicode)
{

}

void Zdarzenie::Pusc_klawisz(SDLKey sym, SDLMod mod, Uint16 unicode)
{

}

void Zdarzenie::Wyjscie()
{

}
