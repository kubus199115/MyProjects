#ifndef _POWIERZCHNIA_H
#define _POWIERZCHNIA_H

#include "SDL/SDL.h"
#include "SDL/SDL_image.h"
#include "SDL/SDL_ttf.h"

class Powierzchnia
{
public:
    Powierzchnia();
    static SDL_Surface *Laduj_Obraz(char *plik);
    static void Rysuj_Obraz(int x, int y, SDL_Surface *powierzchnia, SDL_Surface *ekran, SDL_Rect *czastka);
    static SDL_Surface *Laduj_Tekst(int R, int G, int B, const char *tekst, char *plik, int rozmiar);
};

#endif // _POWIERZCHNIA_H
