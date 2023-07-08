#include "Interfejs.h"

Interfejs Interfejs::Kontrola_Interfejsu;

Interfejs::Interfejs()
{
    tekst_zycia = NULL;
    tekst_pkt = NULL;
    tekst_lvl = NULL;
    wynik_tlo = NULL;
}

void Interfejs::Laduj_Interfejs(int ilosc_zyc, int ilosc_kulek, int max_kulek)
{
    std::stringstream zycia;
    zycia << "LIFE x" << ilosc_zyc;
    tekst_zycia = Powierzchnia::Laduj_Tekst(0,0,0,zycia.str().c_str(),"czcionki/Fipps-Regular.otf", 18);

    std::stringstream kulki;
    kulki << "BALL " << ilosc_kulek << "/" << max_kulek;
    tekst_pkt = Powierzchnia::Laduj_Tekst(0,0,0,kulki.str().c_str(),"czcionki/Fipps-Regular.otf", 18);
}

void Interfejs::Rysuj_Interfejs(SDL_Surface *ekran)
{
   Powierzchnia::Rysuj_Obraz(5,5,tekst_zycia,ekran,NULL);
   Powierzchnia::Rysuj_Obraz(650,5,tekst_pkt,ekran,NULL);
}

void Interfejs::Laduj_Wynik(int wynikl, int ktory_napis)
{
    std::stringstream wynik;
    if(ktory_napis == 0)
        wynik << "YOUR SCORE: " << wynikl;
    else wynik << "YOUR FINAL SCORE: " << wynikl;
    wynik_tlo = Powierzchnia::Laduj_Obraz("grafika/poziom/wynik_tlo.png");
    tekst_lvl = Powierzchnia::Laduj_Tekst(255,255,255,wynik.str().c_str(),"czcionki/Fipps-Regular.otf", 34);
}

void Interfejs::Rysuj_Wynik(SDL_Surface *ekran)
{
    Powierzchnia::Rysuj_Obraz(0,0,wynik_tlo,ekran, NULL);
    Powierzchnia::Rysuj_Obraz((EKRAN_SZEROKOSC - tekst_lvl->w) / 2 ,(EKRAN_WYSOKOSC - tekst_lvl->h) / 2 ,tekst_lvl,ekran,NULL);
}

void Interfejs::Czysc()
{
    SDL_FreeSurface(tekst_pkt);
    SDL_FreeSurface(tekst_zycia);
    SDL_FreeSurface(tekst_lvl);
    SDL_FreeSurface(wynik_tlo);
}
