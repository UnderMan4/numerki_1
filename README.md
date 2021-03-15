# Zadanie 1 (4 tydzień)
Celem zadania pierwszego jest zaimplementowanie i porównanie ze sobą dwóch metod rozwiązywania (znajdowania miejsca zerowego) równań nieliniowych. Każda z grup laboratoryjnych implementuje metodę bisekcji oraz jeden z wariantów przydzielony przez prowadzącego. Dostępne warianty to:

Metody wyznaczania miejsca zerowego:
Wariant 0: Metoda bisekcji
Wariant 1: Metoda stycznych
Wariant 2: Metoda siecznych
Wariant 3: Regula falsi

Oszacowanie dokładności wyniku:
Wariant A: |xi−xi−1|<ε
Wariant B: |f(xi)|<ε

Każda z grup laboratoryjnych implementuje dwa kryteria stopu: 1. osiągnięcie zadanej dokładności obliczeń (wariant A lub B powyżej); 2. wykonanie określonej przez użytkownika liczby iteracji.

Program ma mieć wbudowane kilka różnych funkcji nieliniowych: wielomian, trygonometryczną, wykładniczą i ich złożenia. Użytkownik wybiera jedną z funkcji, określa przedział na którym poszukiwane jest miejsce zerowe oraz wybiera kryterium zatrzymania algorytmu: a) spełnienie warunku nałożonego na dokładność albo b) osiągnięcie zadanej liczby iteracji. Następnie użytkownik wprowadza ε (w przypadku wybrania pierwszego kryterium) lub ilość iteracji (w przypadku wyboru drugiego kryterium). Program wykonuje obliczenia przy użyciu obu metod (bisekcja oraz jeden z przydzielonych wariantów), wyświetla wyniki i rysuje wykres wybranej funkcji na zadanym przedziale, zaznaczając rozwiązania na wykresie. Program ma sprawdzać poprawność założenia o przeciwnych znakach funkcji na krańcach badanego przedziału. Nie trzeba sprawdzać prawdziwości założeń o stałym znaku pochodnych na przedziale. W przypadku metody stycznych dozwolone jest zakodowanie wartości pochodnej na sztywno, nie trzeba jej liczyć numerycznie.

W sprawozdaniu oszacować dokładność obu metod. Porównać ich skuteczność: ile iteracji potrzebna do osiągnięcia zadanej dokładności przy użyciu każdej z metod (która wymaga mniej?) i jaką dokładność osiąga każda metoda dla określonej liczby iteracji (która jest dokładniejsza?). Co się dzieje, jeśli założenie o stałym znaku pochodnych na przedziale nie jest spełnione? Zamieścić w sprawozdaniu przykład ilustrujący taką sytuację.

Prowadzący może zrezygnować z porównywania ze sobą dwóch metod znajdowania miejsca zerowego. W tej sytuacji zamiast metody bisekcji należy zaimplementować metodę optymalizacji jednowymiarowej na przedziale w wariancie przydzielonym przez prowadzącego.

Metody znajdowania maksimum:
Wariant 4: Metoda dychotomii
Wariant 5: Metoda Fibonacci
Wariant 6: Metoda złotego podziału

Ponieważ metody te działają poprawnie jedynie na przedziale unimodalnym, należy zaimplementować wyznaczanie przedziału unimodalnego z podaną przez użytkownika dokładnością. Program powinien umożliwiać wybranie jednego z dwóch warunków stopu. Pierwszy warunek stopu to wykonanie zadanej ilości iteracji. Drugi warunek stopu to zawężenie przedziału poszukiwań do wartości podanej przez użytkownika. W sprawozdaniu należy zamieścić analizę zaimplementowanej metody (analogicznie do analizy metod szukania miejsca zerowego).

Sprawozdanie w formacie PDF oraz kod źródłowy należy umieścić tutaj.
