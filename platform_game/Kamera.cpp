#include "Kamera.h"

Kamera Kamera::Kontrola_Kamery;

Kamera::Kamera()
{
    Pozycja.x = 0;
    Pozycja.y = 0;
    Pozycja.w = EKRAN_SZEROKOSC;
    Pozycja.h = EKRAN_WYSOKOSC;
}

void Kamera::Ustaw_Kamere(int x, int y)
{
    Pozycja.x = (x + BOHATER_SZEROKOSC / 2) - EKRAN_SZEROKOSC / 2;
    Pozycja.y = (y + BOHATER_WYSOKOSC / 2) - EKRAN_WYSOKOSC / 2;

    if(Pozycja.x < 0) Pozycja.x = 0;
    if(Pozycja.x > POZIOM_SZEROKOSC - Pozycja.w) Pozycja.x = POZIOM_SZEROKOSC - Pozycja.w;
    if(Pozycja.y < 0) Pozycja.y = 0;
    if(Pozycja.y > POZIOM_WYSOKOSC - Pozycja.h) Pozycja.y = POZIOM_WYSOKOSC - Pozycja.h;
}
