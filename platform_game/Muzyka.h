#ifndef MUZYKA_H
#define MUZYKA_H

#include "SDL/SDL_mixer.h"


class Muzyka
{
private:
    Mix_Music *muzyka = NULL;
public:
    Muzyka();

    bool Laduj(char *plik);
    bool Graj();
    void Zatrzymaj();
    void Czysc();
};

#endif // MUZYKA_H
