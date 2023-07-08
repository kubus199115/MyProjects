#ifndef _ANIMACJA_H
#define _ANIMACJA_H

#include "SDL/SDL.h"

class Animacja
{
private:
    long Stary_czas;
    int Czestotliwosc;
    int Aktualna_klatka;
public:
    int Ilosc_klatek;
    int Co_Ile;
public:
    Animacja();
    void Animuj();
    void Ustaw_Czestotliwosc(int Czestotliwosc);
    void Ustaw_Aktualna_klatke(int Aktualna_klatka);
    int Zwroc_Aktualna_Klatke();
};

#endif // _ANIMACJA_H

