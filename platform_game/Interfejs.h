#ifndef _INTERFEJS_H
#define _INTERFEJS_H

#include "SDL/SDL.h"
#include <string>
#include <sstream>
#include "Powierzchnia.h"
#include "Stale.h"

class Interfejs
{
public:
    static Interfejs Kontrola_Interfejsu;
private:
    SDL_Surface *tekst_zycia;
    SDL_Surface *tekst_pkt;
    SDL_Surface *tekst_lvl;
    SDL_Surface *wynik_tlo;
public:
    Interfejs();
    void Rysuj_Interfejs(SDL_Surface *ekran);
    void Laduj_Interfejs(int ilosc_zyc, int ilosc_kulek, int max_kulek);
    void Rysuj_Wynik(SDL_Surface *ekran);
    void Laduj_Wynik(int wynik, int ktory_napis);
    void Czysc();
};

#endif // _INTERFEJS_H
