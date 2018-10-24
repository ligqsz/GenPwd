#ifndef _SHA1_H_
#define _SHA1_H_

#include <stdint.h>

typedef struct {
    __uint32_t state[5];
    __uint32_t count[2];
    unsigned char buffer[256];
} SHA1_CTX;  

#ifdef __cplusplus
extern "C"
{
#endif

void SHA1_Init(SHA1_CTX* context);  
void SHA1_Update(SHA1_CTX* context, const void *data, size_t len);  
void SHA1_Final(unsigned char *md, SHA1_CTX* context); 

#ifdef __cplusplus
}
#endif

#endif