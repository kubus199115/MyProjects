#ifndef _GRA_H
#define _GRA_H

#include "SDL/SDL.h"
#include <string>
#include <sstream>
#include "Zdarzenie.h"
#include "FPS.h"
#include "Obiekt.h"
#include "Interfejs.h"
#include "Muzyka.h"

class Gra : public Zdarzenie
{
private:
    SDL_Surface *ekran;
    bool Koniec;

    FPS FPSKontrola;

    Obiekt Bohater;
    Muzyka muzyka;

    int Ilosc_lvl;

    int ilosc_punktow;
    int ilosc_punktow_koncowa;

    double czas_poziomu;

    bool wyswietl_wynik;
    bool ostatni_lvl;
public:
    Gra();
    bool Laduj();
    void Rysuj();
    void Ruszaj();
    bool Inicjuj();
    void Czysc();
    void Koncz_Gre();
    void Obsluga_Zdarzen(SDL_Event *zdarzenie);
    int Dzialaj();
    void Pokaz_wynik(double czas, int ilosc_kulek);
    void Resetuj();

    void Wcisnij_klawisz(SDLKey sym, SDLMod mod, Uint16 unicode);
    void Pusc_klawisz(SDLKey sym, SDLMod mod, Uint16 unicode);
    void Wyjscie();
};

#endif // _GRA_H
