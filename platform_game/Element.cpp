#include "Element.h"

Element::Element()
{
    Pozycja.x = 0;
    Pozycja.y = 0;
    Pozycja.w = KAFEL_SZEROKOSC;
    Pozycja.h = KAFEL_WYSOKOSC;
    Pozycja_k.x = 0;
    Pozycja_k.y = 0;
    Pozycja_p.x = 0;
    Pozycja_p.y = 0;

    ruch_prawo = true;
    ruch_gora = true;

    typ = 0;
}

int Element::Zwroc_typ()
{
    return typ;
}
