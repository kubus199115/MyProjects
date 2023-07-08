#include "Obiekt.h"

Obiekt::Obiekt()
{
    Klatka_Anim.x = 0;
    Klatka_Anim.y = 0;
    Klatka_Anim.w = 0;
    Klatka_Anim.h = 0;

    Pozycja.x = 40;
    Pozycja.y = 400;
    Pozycja.w = BOHATER_SZEROKOSC;
    Pozycja.h = BOHATER_WYSOKOSC;

    Pozycja_poczatkowa_x = 40;
    Pozycja_poczatkowa_y = 400;

    PredkoscX = 0;
    PredkoscY = 0;

    MaxPredkoscX = 1;
    MaxPredkoscY = 1;

    Ilosc_klatek = 0;

    Powierzchnia_Obiektu = NULL;

    Ruch_Prawo = false;
    Ruch_Lewo = false;
    Mozna_Skakac = false;

    Stan_Animacji = 0;

    Zgon = false;
    Ilosc_Zyc = 3;
    Ilosc_Kulek = 0;

    grawitacja = true;
    czas_skok = 200;
}

void Obiekt::Ruszaj()
{
    if(Ruch_Lewo == false and Ruch_Prawo == false) Przerwij_Poruszanie();
    if(Ilosc_Zyc == 0) Zgon = true;

    if(grawitacja) PredkoscY += 1;
    else
    {
        skok_licz_stop = SDL_GetTicks() - skok_licz_start;
        if(skok_licz_stop < czas_skok)
        {
            PredkoscY -= 10 / (skok_licz_stop * 0.1);
        }
        else
        {
            grawitacja = true;
            skok_licz_start = 0;
        }
    }

    Pozycja.x += PredkoscX;

    if(Pozycja.x < 0 or (Pozycja.x + BOHATER_SZEROKOSC) > POZIOM_SZEROKOSC or Pozycja_Kolizja(Pozycja) == false)
        Pozycja.x -= PredkoscX;

    Pozycja.y += PredkoscY;

    if(Pozycja_Kolizja(Pozycja) == false)
    {
        Pozycja.y -= PredkoscY;
        Mozna_Skakac = true;
    }
    else Mozna_Skakac = false;

    if((Pozycja.y + BOHATER_WYSOKOSC) > POZIOM_WYSOKOSC + BOHATER_WYSOKOSC)
    {
        SDL_Delay(1000);
        Ilosc_Zyc--;
        Pozycja.x = Pozycja_poczatkowa_x;
        Pozycja.y = Pozycja_poczatkowa_y;
    }

    Element_Kolizja(Pozycja);

    if(PredkoscX > MaxPredkoscX) PredkoscX = MaxPredkoscX;
    if(PredkoscX < -MaxPredkoscX) PredkoscX = -MaxPredkoscX;
    if(PredkoscY > MaxPredkoscY) PredkoscY = MaxPredkoscY;
    if(PredkoscY <-MaxPredkoscY) PredkoscY = -MaxPredkoscY;

    Animuj();
    Poruszanie();
}

void Obiekt::Animuj()
{
    if(Ruch_Prawo)
    {
        Stan_Animacji = 0;
        Kontrola_Animacji.Co_Ile = 1;
    }
    else if(Ruch_Lewo)
    {
        Stan_Animacji = 1;
        Kontrola_Animacji.Co_Ile = 1;
    }
    else Kontrola_Animacji.Co_Ile = 0;

    Kontrola_Animacji.Animuj();
}

void Obiekt::Skacz()
{
    if(Mozna_Skakac == false) return;

    skok_licz_start = SDL_GetTicks();
    grawitacja = false;
    //PredkoscY -= 5;
}

void Obiekt::Rysuj(SDL_Surface *ekran)
{
    Kamera::Kontrola_Kamery.Ustaw_Kamere(Pozycja.x, Pozycja.y);

    Klatka_Anim.x = Kontrola_Animacji.Zwroc_Aktualna_Klatke() * Pozycja.w;
    Klatka_Anim.y = Stan_Animacji * Pozycja.h;
    Powierzchnia::Rysuj_Obraz(Pozycja.x - Kamera::Kontrola_Kamery.Pozycja.x, Pozycja.y - Kamera::Kontrola_Kamery.Pozycja.y, Powierzchnia_Obiektu, ekran, &Klatka_Anim);
}

void Obiekt::Poruszanie()
{
    if(Ruch_Prawo)
    {
        PredkoscX += 1;
    }
    else if(Ruch_Lewo)
    {
        PredkoscX -= 1;
    }
}

void Obiekt::Przerwij_Poruszanie()
{
    if(PredkoscX > 0) PredkoscX-=1;
    if(PredkoscX < 0) PredkoscX+=1;

    if(PredkoscX > -1 and PredkoscX < 1)
    {
        PredkoscX = 0;
    }
}

bool Obiekt::Laduj(char *plik, int szerokosc, int wysokosc, int Ilosc_klatek)
{
    Powierzchnia_Obiektu = Powierzchnia::Laduj_Obraz(plik);
    if(Powierzchnia_Obiektu == NULL) return false;

    Pozycja.w = szerokosc;
    Pozycja.h = wysokosc;
    Klatka_Anim.w = szerokosc;
    Klatka_Anim.h = wysokosc;
    Kontrola_Animacji.Ilosc_klatek = Ilosc_klatek;

    return true;
}

void Obiekt::Czysc()
{
    SDL_FreeSurface(Powierzchnia_Obiektu);
}

bool Obiekt::Sprawdz_Kolizje(SDL_Rect A, SDL_Rect B)
{
    int GoraA, GoraB;
    int DolA, DolB;
    int PrawoA, PrawoB;
    int LewoA, LewoB;

    GoraA = A.y;
    DolA = A.y + A.h;
    PrawoA = A.x + A.w;
    LewoA = A.x;

    GoraB = B.y;
    DolB = B.y + B.h;
    PrawoB = B.x + B.w;
    LewoB = B.x;

    if(DolA <= GoraB) return false;
    if(GoraA >= DolB) return false;
    if(PrawoA <= LewoB) return false;
    if(LewoA >= PrawoB) return false;

    return true;
}

bool Obiekt::Sprawdz_Kolizje_O(SDL_Rect A, SDL_Rect B)
{
    float odleglosc = 0;
    float srodekAx, srodekAy, srodekBx, srodekBy;

    srodekAx = A.x + (A.w / 2);
    srodekAy = A.y + (A.h / 2);
    srodekBx = B.x + (B.w / 2);
    srodekBy = B.y + (B.h / 2);

    odleglosc = sqrtf(powf(srodekAx - srodekBx,2) + powf(srodekAy - srodekBy,2));

    if(fabsf(odleglosc) < ((A.w / 2) + (B.w / 2))) return true;

    return false;
}

bool Obiekt::Pozycja_Kolizja(SDL_Rect P)
{
    for(int i=0; i<Poziom::Kontrola_Poziomu.Lista_Kafli.size(); i++)
    {
        if(Poziom::Kontrola_Poziomu.Lista_Kafli[i].typ != 0)
        {
            if(Sprawdz_Kolizje(P, Poziom::Kontrola_Poziomu.Lista_Kafli[i].Pozycja) == true) return false;
        }
    }
    return true;
}

void Obiekt::Element_Kolizja(SDL_Rect P)
{
    for(int i=0; i<Poziom::Kontrola_Poziomu.Lista_Elementow.size(); i++)
    {
        if(Sprawdz_Kolizje_O(P, Poziom::Kontrola_Poziomu.Lista_Elementow[i].Pozycja) == true)
        {
            Jaki_Element = Poziom::Kontrola_Poziomu.Lista_Elementow[i].Zwroc_typ();
            if(Jaki_Element == 0 or Jaki_Element == 2) Poziom::Kontrola_Poziomu.Lista_Elementow[i].Pozycja.x = -100; //przesuwamy gdzies poza ekran
            Ktory_Element(Jaki_Element);
        }
    }
}

void Obiekt::Ktory_Element(int typ)
{
    if(typ == 0)
    {
        Ilosc_Kulek++;
        if(Ilosc_Kulek == Poziom::Kontrola_Poziomu.liczba_kulek)
        {
            Poziom::Kontrola_Poziomu.Koniec_poziomu = true;
            Poziom::Kontrola_Poziomu.Aktualny_poziom++;
            Ilosc_Kulek = 0;
            Pozycja.x = Pozycja_poczatkowa_x;
            Pozycja.y = Pozycja_poczatkowa_y;
        }
    }
    else if(typ == 1 or typ == 3 or typ == 4 or typ == 5)
    {
        SDL_Delay(1000);
        Ilosc_Zyc--;
        Pozycja.x = Pozycja_poczatkowa_x;
        Pozycja.y = Pozycja_poczatkowa_y;
    }
    else if(typ == 2)
    {
        Ilosc_Zyc++;
    }
}

