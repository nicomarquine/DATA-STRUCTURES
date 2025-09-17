#include <stdio.h>
#include <stdlib.h>


// Define a Point data type as a struct
typedef struct {
    double x;
    double y;

} Point;

// Function to compute the centroid of an array of points
Point computeCentroid(const Point *points, size_t n){
    Point centroid = {0.0, 0.0};

    if (n==0){
        return centroid;
    }

    double sumX = 0.0, sumY = 0.0;
    for (int i = 0; i < n; i++){
        sumX += points[i].x;
        sumY += points[i].y;
    }

    centroid.x = sumX / n;
    centroid.y = sumY / n;

    return centroid;
}

// Function to print a point (passed as a constant pointer)
void printPoint(const Point *p) {
    printf("(%.2f, %.2f)", p->x, p->y);
}

int main(){
    Point points[] = {
        {1.0, 2.0},
        {3.0, 4.0},
        {5.0, 0.0},
        {2.0, 1.0}
    };
    size_t n = sizeof(points) / sizeof(points[0]);

    printf("Predefined points:\n");
    for (int i = 0; i < n; i++) {
        printPoint(&points[i]);
        printf("\n");
    }

    Point centroid = computeCentroid(points, n);
    printf("Centroid of predefined points: ");
    printPoint(&centroid);
    printf("\n\n");
}
