#include "Powierzchnia.h"

Powierzchnia::Powierzchnia()
{

}

SDL_Surface *Powierzchnia::Laduj_Obraz(char *plik)
{
    SDL_Surface *powierzchnia = NULL;
    SDL_Surface *powierzchnia_zwracana = NULL;

    powierzchnia = IMG_Load(plik);

    if(powierzchnia != NULL)
    {
        powierzchnia_zwracana = SDL_DisplayFormatAlpha(powierzchnia);
        SDL_FreeSurface(powierzchnia);
    }

    return powierzchnia_zwracana;
}

void Powierzchnia::Rysuj_Obraz(int x, int y, SDL_Surface *powierzchnia, SDL_Surface *ekran, SDL_Rect *czastka = NULL)
{
    SDL_Rect prostokat;

    prostokat.x = x;
    prostokat.y = y;

    SDL_BlitSurface(powierzchnia, czastka, ekran, &prostokat);
}

SDL_Surface *Powierzchnia::Laduj_Tekst(int R, int G, int B, const char *tekst, char *plik, int rozmiar)
{
    SDL_Surface *powierzchnia_tekstu = NULL;
    TTF_Font *czcionka = TTF_OpenFont(plik, rozmiar);
    SDL_Color KolorT = {R,G,B};
    powierzchnia_tekstu = TTF_RenderText_Solid(czcionka, tekst, KolorT);
    TTF_CloseFont(czcionka);

    return powierzchnia_tekstu;
}
