#include "Poziom.h"

Poziom Poziom::Kontrola_Poziomu;

Poziom::Poziom()
{
    Powierzchnia_Poziomu = NULL;
    Powierzchnia_Tla = NULL;
    Powierzchnia_Elementu = NULL;
    wielkosc = 0;
    liczba_kulek = 0;
    liczba_elementow = 0;
    liczba_elementow_ruch = 0;
    Koniec_poziomu = false;
    Aktualny_poziom = 1;
}

bool Poziom::Laduj(int ktory)
{
    Lista_Kafli.clear();

    char *plik;

    switch(ktory)
    {
        case 1: plik = "poziom/lvl1.txt";break;
        case 2: plik = "poziom/lvl2.txt";break;
        case 3: plik = "poziom/lvl3.txt";break;
        case 4: plik = "poziom/lvl4.txt";break;
        case 5: plik = "poziom/lvl5.txt";break;
        case 6: plik = "poziom/lvl6.txt";break;
        case 7: plik = "poziom/lvl7.txt";break;
        case 8: plik = "poziom/lvl8.txt";
    }

    FILE *uchwyt = fopen(plik, "r");

    fscanf(uchwyt, "%d\n", &wielkosc);

    char plik_grafika[255];
    fscanf(uchwyt, "%s\n", plik_grafika);
    Powierzchnia_Poziomu = Powierzchnia::Laduj_Obraz(plik_grafika);
    if(Powierzchnia_Poziomu == NULL) return false;

    char plik_tlo[255];
    fscanf(uchwyt, "%s\n", plik_tlo);
    Powierzchnia_Tla = Powierzchnia::Laduj_Obraz(plik_tlo);
    if(Powierzchnia_Tla == NULL) return false;

    char plik_element[255];
    fscanf(uchwyt, "%s\n", plik_element);
    Powierzchnia_Elementu = Powierzchnia::Laduj_Obraz(plik_element);
    if(Powierzchnia_Elementu == NULL) return false;

    char plik_lvl[255];
    fscanf(uchwyt, "%s\n", plik_lvl);

    fscanf(uchwyt, "%d\n", &liczba_kulek);

    fscanf(uchwyt, "%d\n", &liczba_elementow);

    fscanf(uchwyt, "%d\n", &liczba_elementow_ruch);

    for(int i=0; i<liczba_elementow; i++)
    {
        Element _Element;
        fscanf(uchwyt, "%d:%d:%d", &_Element.Pozycja.x, &_Element.Pozycja.y, &_Element.typ);
        fscanf(uchwyt, "\n");
        _Element.Pozycja.w = KAFEL_SZEROKOSC;
        _Element.Pozycja.h = KAFEL_WYSOKOSC;
        Lista_Elementow.push_back(_Element);
    }

    for(int i=0; i<liczba_elementow_ruch; i++)
    {
        Element _Element;
        fscanf(uchwyt, "%d:%d:%d:%d:%d:%d:%d", &_Element.Pozycja.x, &_Element.Pozycja.y, &_Element.Pozycja_p.x, &_Element.Pozycja_p.y,
                &_Element.Pozycja_k.x, &_Element.Pozycja_k.y, &_Element.typ);
        fscanf(uchwyt, "\n");
        _Element.Pozycja.w = KAFEL_SZEROKOSC;
        _Element.Pozycja.h = KAFEL_WYSOKOSC;
        Lista_Elementow.push_back(_Element);
    }

    fclose(uchwyt);

    FILE *uchwyt2 = fopen(plik_lvl, "r");

    int wysokosc = EKRAN_WYSOKOSC / KAFEL_WYSOKOSC;
    int szerokosc = (EKRAN_SZEROKOSC * wielkosc) / KAFEL_SZEROKOSC;

    for(int i=0; i<wysokosc; i++)
    {
        for(int j=0; j<szerokosc; j++)
        {
            Kafel _Kafel;
            fscanf(uchwyt2, "%d ", &_Kafel.typ);
            _Kafel.Pozycja.x = j * KAFEL_SZEROKOSC;
            _Kafel.Pozycja.y = i * KAFEL_WYSOKOSC;

            Lista_Kafli.push_back(_Kafel);
        }
        fscanf(uchwyt2, "\n");
    }

    fclose(uchwyt2);

    return true;
}

void Poziom::Rysuj(SDL_Surface *ekran)
{
    Powierzchnia::Rysuj_Obraz(0, 0, Powierzchnia_Tla, ekran, NULL);

    for(int i=0; i<Lista_Kafli.size(); i++)
    {
        if(Lista_Kafli[i].typ == 0) continue;

        int X = Lista_Kafli[i].Pozycja.x;
        int Y = Lista_Kafli[i].Pozycja.y;

        SDL_Rect Typ;
        Typ.x = (Lista_Kafli[i].typ * KAFEL_SZEROKOSC) - KAFEL_SZEROKOSC;
        Typ.y = 0;
        Typ.w = KAFEL_SZEROKOSC;
        Typ.h = KAFEL_WYSOKOSC;

        Powierzchnia::Rysuj_Obraz(X - Kamera::Kontrola_Kamery.Pozycja.x , Y - Kamera::Kontrola_Kamery.Pozycja.y, Powierzchnia_Poziomu, ekran, &Typ);
    }

    for(int i=0; i<liczba_elementow + liczba_elementow_ruch; i++)
    {
        int X = Lista_Elementow[i].Pozycja.x;
        int Y = Lista_Elementow[i].Pozycja.y;

        SDL_Rect Typ;
        Typ.x = (Lista_Elementow[i].typ * KAFEL_SZEROKOSC);
        Typ.y = 0;
        Typ.w = KAFEL_SZEROKOSC;
        Typ.h = KAFEL_WYSOKOSC;

        Powierzchnia::Rysuj_Obraz(X - Kamera::Kontrola_Kamery.Pozycja.x , Y - Kamera::Kontrola_Kamery.Pozycja.y, Powierzchnia_Elementu, ekran, &Typ);
    }
}

void Poziom::Ruszaj()
{
    for(int i=0; i<liczba_elementow_ruch + liczba_elementow; i++)
    {
        if(Lista_Elementow[i].typ != 3 and Lista_Elementow[i].typ != 4 and Lista_Elementow[i].typ != 5) continue;

        if(Lista_Elementow[i].ruch_prawo) Lista_Elementow[i].Pozycja.x++;
        else Lista_Elementow[i].Pozycja.x--;

        if(Lista_Elementow[i].Pozycja.x == Lista_Elementow[i].Pozycja_k.x) Lista_Elementow[i].ruch_prawo = false;
        else if(Lista_Elementow[i].Pozycja.x == Lista_Elementow[i].Pozycja_p.x) Lista_Elementow[i].ruch_prawo = true;
    }
}

void Poziom::Czysc()
{
    SDL_FreeSurface(Powierzchnia_Poziomu);
    SDL_FreeSurface(Powierzchnia_Tla);
    SDL_FreeSurface(Powierzchnia_Elementu);
    Lista_Kafli.clear();
    Lista_Elementow.clear();
}


