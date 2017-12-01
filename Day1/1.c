#include <stdio.h>
#include <string.h>

int main(int argc, char **argv) {

    if (argc != 2) {
        printf("Usage %s input\n", *argv);
    }

    char *input = *(argv+1);
    int length = strlen(input);
    long sum = 0;

    for (int i = 0; i < length; i++) {
        char x = input[i];
        char y = input[i+1];
        sum += x == y ? x-'0' : 0;
    }

    sum += input[0] == input[length-1] ? input[0]-'0' : 0;
    
    printf("%ld\n", sum);
}
