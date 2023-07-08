#ifndef OBIEKT_H
#define OBIEKT_H

#include "Stale.h"
#include "Powierzchnia.h"
#include "Animacja.h"
#include "Kamera.h"
#include "Poziom.h"

#include "SDL/SDL.h"
#include <vector>
#include <cmath>

class Obiekt
{
private:
    SDL_Surface *Powierzchnia_Obiektu;

    SDL_Rect Pozycja;
    SDL_Rect Klatka_Anim;

    int PredkoscX;
    int PredkoscY;

    int MaxPredkoscX;
    int MaxPredkoscY;

    int Ilosc_klatek;

    bool grawitacja;

    int Pozycja_poczatkowa_x;
    int Pozycja_poczatkowa_y;

    int Jaki_Element;

    Animacja Kontrola_Animacji;

    int Stan_Animacji;
    int Numer_Klatki;

    bool Mozna_Skakac;
    double czas_skok;
    double skok_licz_start;
    double skok_licz_stop;

public:
    bool Ruch_Prawo;
    bool Ruch_Lewo;
    bool Skok;

    bool Zgon;
    int Ilosc_Zyc;
    int Ilosc_Kulek;

public:
    Obiekt();

    void Ruszaj();
    void Rysuj(SDL_Surface *ekran);
    void Poruszanie();
    bool Laduj(char *plik, int szerokosc, int wysokosc, int Ilosc_klatek);
    void Czysc();
    void Animuj();
    void Przerwij_Poruszanie();

    bool Pozycja_Kolizja(SDL_Rect P);
    void Element_Kolizja(SDL_Rect P);
    bool Sprawdz_Kolizje(SDL_Rect A, SDL_Rect B);
    void Ktory_Element(int typ);
    bool Sprawdz_Kolizje_O(SDL_Rect A, SDL_Rect B);

    void Skacz();
};

#endif // OBIEKT_H
