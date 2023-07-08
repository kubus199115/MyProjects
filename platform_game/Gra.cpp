#include "Gra.h"

Gra::Gra()
{
    ekran = NULL;
    Koniec = false;
    Ilosc_lvl = 8;
    czas_poziomu = 0;
    ilosc_punktow = 0;
    ilosc_punktow_koncowa = 0;
    wyswietl_wynik = true;
    ostatni_lvl = false;
}

//Ladowanie plikow

bool Gra::Laduj()
{
    if(Poziom::Kontrola_Poziomu.Laduj(Poziom::Kontrola_Poziomu.Aktualny_poziom) == false) return false;

    if(Bohater.Laduj("grafika/obiekt/mrcube.png", 35, 35, 10) == false) return false;

    if(muzyka.Laduj("muzyka/muzyka.wav") == false) return false;

    return true;
}

//Inicjowanie

bool Gra::Inicjuj()
{
    if(SDL_Init(SDL_INIT_EVERYTHING) == -1) return false;

    ekran = SDL_SetVideoMode(EKRAN_SZEROKOSC, EKRAN_WYSOKOSC, 32, SDL_SWSURFACE | SDL_FULLSCREEN);
    if (ekran == NULL) return false;

    if(TTF_Init() == -1) return false;

    if(Mix_OpenAudio(22050, MIX_DEFAULT_FORMAT, 2, 4096) == -1) return false;

    SDL_ShowCursor(SDL_DISABLE);

    return true;
}

//Rysowanie

void Gra::Rysuj()
{
    Interfejs::Kontrola_Interfejsu.Laduj_Interfejs(Bohater.Ilosc_Zyc, Bohater.Ilosc_Kulek, Poziom::Kontrola_Poziomu.liczba_kulek);

    Poziom::Kontrola_Poziomu.Rysuj(ekran);

    Interfejs::Kontrola_Interfejsu.Rysuj_Interfejs(ekran);

    Bohater.Rysuj(ekran);

    SDL_Flip(ekran);
}

//Ruszanie

void Gra::Ruszaj()
{
    Bohater.Ruszaj();
    Poziom::Kontrola_Poziomu.Ruszaj();
}
//Obsluga zdarzen

void Gra::Wyjscie()
{
    Koniec = true;
    wyswietl_wynik = false;
}

void Gra::Wcisnij_klawisz(SDLKey sym, SDLMod mod, Uint16 unicode)
{
    switch(sym)
    {
    case SDLK_RIGHT:
        {
            Bohater.Ruch_Prawo = true;
            break;
        }
    case SDLK_LEFT:
        {
            Bohater.Ruch_Lewo = true;
            break;
        }
    case SDLK_SPACE:
        {
            Bohater.Skacz();
            break;
        }
    case SDLK_q:
        Wyjscie();
    }
}

void Gra::Pusc_klawisz(SDLKey sym, SDLMod mod, Uint16 unicode)
{
    switch(sym)
    {
    case SDLK_RIGHT:
        {
            Bohater.Ruch_Prawo = false;
            break;
        }
    case SDLK_LEFT:
        {
            Bohater.Ruch_Lewo = false;
        }
    }
}

void Gra::Obsluga_Zdarzen(SDL_Event *zdarzenie)
{
    Zdarzenie::Obsluga_Zdarzen(zdarzenie);
}

//Czyszczenie

void Gra::Czysc()
{
    Bohater.Czysc();

    Interfejs::Kontrola_Interfejsu.Czysc();

    Poziom::Kontrola_Poziomu.Czysc();

    muzyka.Czysc();
}

void Gra::Koncz_Gre()
{
    SDL_FreeSurface(ekran);

    TTF_Quit();
    Mix_CloseAudio();
    SDL_Quit();
}

//Glowna petla gry

void Gra::Resetuj()
{
    Poziom::Kontrola_Poziomu.Aktualny_poziom = 1;
    Bohater.Zgon = false;
    ilosc_punktow = 0;
    ilosc_punktow_koncowa = 0;
    Bohater.Ilosc_Kulek = 0;
    Bohater.Ilosc_Zyc = 3;
}

void Gra::Pokaz_wynik(double czas, int ilosc_kulek)
{
    ilosc_punktow = (ilosc_kulek * 100000) / (int)czas;
    ilosc_punktow_koncowa += ilosc_punktow;
    if(wyswietl_wynik == true)
    {
        if(ostatni_lvl == false)
        {
            Interfejs::Kontrola_Interfejsu.Laduj_Wynik(ilosc_punktow, 0);
        }
        else Interfejs::Kontrola_Interfejsu.Laduj_Wynik(ilosc_punktow_koncowa, 1);
        Interfejs::Kontrola_Interfejsu.Rysuj_Wynik(ekran);
        SDL_Flip(ekran);
        SDL_Delay(5000);
    }
}

int Gra::Dzialaj()
{
    SDL_Event Zdarzenie;

    if(Inicjuj() == false) return 1;

    while(Koniec != true)
    {
        if(Laduj() == false) return 1;

        czas_poziomu = SDL_GetTicks();

        if(muzyka.Graj() == false) return 1;

        while(Poziom::Kontrola_Poziomu.Koniec_poziomu != true and Koniec != true and Bohater.Zgon != true)
        {
            FPSKontrola.Startuj();
            while(SDL_PollEvent(&Zdarzenie))
            {
                Obsluga_Zdarzen(&Zdarzenie);
            }

            Ruszaj();
            Rysuj();

            if(FPSKontrola.PobierzCzas() < 1000 / KLATKI_NA_SEKUNDE)
                SDL_Delay((1000 / KLATKI_NA_SEKUNDE ) - FPSKontrola.PobierzCzas());
        }

        czas_poziomu = SDL_GetTicks() - czas_poziomu;
        if(Bohater.Zgon != true)
            Pokaz_wynik(czas_poziomu, Poziom::Kontrola_Poziomu.liczba_kulek);

        muzyka.Zatrzymaj();
        Czysc();
        if(Bohater.Zgon == true)
        {
            Resetuj();
        }
        if(Poziom::Kontrola_Poziomu.Aktualny_poziom <= Ilosc_lvl)
        {
            Poziom::Kontrola_Poziomu.Koniec_poziomu = false;
            if(Poziom::Kontrola_Poziomu.Aktualny_poziom == Ilosc_lvl) ostatni_lvl = true;
        }
        else
        {
            Koniec = true;
        }
    }

    Koncz_Gre();

    return 0;
}

int main(int argc, char* args[])
{
    Gra Platformowka;
    return Platformowka.Dzialaj();
}


