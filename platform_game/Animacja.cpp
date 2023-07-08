#include "Animacja.h"

Animacja::Animacja()
{
    Aktualna_klatka = 0;
    Czestotliwosc = 70;
    Stary_czas = 0;
    Co_Ile = 0;
}

void Animacja::Animuj()
{
    if(Stary_czas + Czestotliwosc > SDL_GetTicks()) return;

    Stary_czas = SDL_GetTicks();

    Aktualna_klatka += Co_Ile;

    if(Aktualna_klatka == Ilosc_klatek) Aktualna_klatka = 0;
}

void Animacja::Ustaw_Aktualna_klatke(int akt_klatka)
{
    Aktualna_klatka = akt_klatka;
}

void Animacja::Ustaw_Czestotliwosc(int czest)
{
    Czestotliwosc = czest;
}

int Animacja::Zwroc_Aktualna_Klatke()
{
    return Aktualna_klatka;
}
