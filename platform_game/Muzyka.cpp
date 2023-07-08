#include "Muzyka.h"

Muzyka::Muzyka()
{

}

bool Muzyka::Laduj(char *plik)
{
    muzyka = Mix_LoadMUS(plik);

    if(muzyka == NULL) return false;

    return true;
}

bool Muzyka::Graj()
{
    if(Mix_PlayMusic(muzyka, -1) == -1) return false;

    return true;
}

void Muzyka::Zatrzymaj()
{
    Mix_HaltMusic();
}

void Muzyka::Czysc()
{
    Mix_FreeMusic(muzyka);
}
