#ifndef _POZIOM_H
#define _POZIOM_H

#include "Kafel.h"
#include "Element.h"
#include "Powierzchnia.h"
#include "Kamera.h"
#include <vector>
#include "SDL/SDL.h"

class Poziom
{
private:
    SDL_Surface *Powierzchnia_Poziomu;
    SDL_Surface *Powierzchnia_Tla;
    SDL_Surface *Powierzchnia_Elementu;

    int wielkosc;
    int liczba_elementow;
    int liczba_elementow_ruch;
public:
    static Poziom Kontrola_Poziomu;
    std::vector<Kafel> Lista_Kafli;
    std::vector<Element> Lista_Elementow;

    int Aktualny_poziom;
    int liczba_kulek;
    bool Koniec_poziomu;

public:
    Poziom();
    bool Laduj(int ktory);
    void Rysuj(SDL_Surface *ekran);
    void Ruszaj();
    void Czysc();
};

#endif // _POZIOM_H
