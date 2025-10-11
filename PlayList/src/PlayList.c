#include <assert.h>
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "PlayList.h"
#include "test/unit/UnitTest.h"


struct PlayList* PlayList_new() {
    struct PlayList* p_playList = malloc(sizeof(struct PlayList));
    assert(p_playList != NULL && "PlayList_new(): not enough memory");
    p_playList -> p_first = NULL;
    p_playList -> p_playing = NULL;
    return p_playList;
}

// private function to allocate a new node on the heap
static struct Node* Node_new(const char* songName, struct Node* p_previous, struct Node* p_next) {
    struct Node* p_node = malloc(sizeof(struct Node));
    assert(p_node != NULL && "Node_new(): not enough memory");
    assert(strlen(songName) <= MAX_NAME_LENGTH && "Node_new(): songName too long");
    strcpy(p_node->songName, songName);
    p_node->p_next = p_next;
    p_node->p_previous = p_previous;
    return p_node;
}

void PlayList_insertAtFront(struct PlayList* p_playList, const char* songName) {
    assert(p_playList != NULL && "PlayList_insertAtFront(): p_playList is NULL");
    struct Node* p_node = Node_new(songName, NULL, p_playList -> p_first);
    if(p_playList -> p_first != NULL){
        p_playList -> p_first -> p_previous = p_node;
    }else{
        p_playList -> p_playing = p_node;
    }
    p_playList -> p_first = p_node;
   
}

void PlayList_insertInOrder(struct PlayList* p_playList, const char* songName) {
    assert(p_playList != NULL && "PlayList_insertInOrder(): p_playList is NULL");
    // todo
    struct Node* p_current = p_playList -> p_first;
    struct Node* p_previous = NULL;

    while (p_current != NULL && strcmp(p_current -> songName, songName) < 0) {
        p_previous = p_current;
        p_current = p_current -> p_next;
    }

    if (p_previous == NULL) {
        PlayList_insertAtFront(p_playList, songName);
    }
    else if (p_current == NULL) {
        PlayList_insertAtEnd(p_playList, songName);
    }

    else {
        struct Node* p_node = Node_new(songName, p_previous, p_current);
        p_previous -> p_next = p_node;
        p_current -> p_previous = p_node;
    }
}

void PlayList_insertAtEnd(struct PlayList* p_playList, const char* songName) {
    assert(p_playList != NULL && "PlayList_insertAtEnd(): p_playList is NULL");
    if(p_playList -> p_first == NULL){
        p_playList -> p_first = Node_new(songName, NULL, NULL);
        p_playList -> p_playing = Node_new(songName, NULL, NULL);
    }else{
        struct Node* p_current = p_playList -> p_first;
        while(p_current -> p_next != NULL){
            p_current = p_current -> p_next;
        }
        struct Node* p_node = Node_new(songName, p_current, NULL);
        p_current -> p_next = p_node;
    }
   
}

// private function to find node with a gigen song name in the playList.
// returns pointer to node containing such song or NULL if song is not in the playList.
static struct Node *FindNode(struct Node* p_first, const char* songName) {
    struct Node* p_current = p_first;
    while ((p_current != NULL) && (strcmp(songName, p_current->songName) != 0)) {
        p_current = p_current->p_next;
    }
    return p_current;
}

bool PlayList_insertAfter(struct PlayList* p_playList, const char* targetSong, const char* newSong) {
    assert(p_playList != NULL && "PlayList_insertAfter(): p_playList is NULL");
    struct Node *p_target = FindNode(p_playList -> p_first, targetSong);
    if(p_target == NULL){
        return false;
    }else{
        struct Node* p_node = Node_new(newSong, p_target, p_target -> p_next);
        p_target -> p_next = p_node;
        if(p_node -> p_next != NULL){
            p_node -> p_next -> p_previous = p_node;
        }
       
        return true;
    }
}

bool PlayList_insertBefore(struct PlayList* p_playList, const char* targetSong, const char* newSong) {
    assert(p_playList != NULL && "PlayList_insertBefore(): p_playList is NULL");
    struct Node *p_target = FindNode(p_playList -> p_first, targetSong);
    if (p_target == NULL) {
        return false;
    }
    struct Node* p_node = Node_new(newSong, NULL, p_target);
    if (p_target -> p_previous == NULL) {
        p_playList -> p_first = p_node;  
    } else {
        p_target -> p_previous -> p_next = p_node;
        p_node -> p_previous = p_target -> p_previous;
    }
    p_target -> p_previous = p_node;
   
    return true;
}

void PlayList_deleteFromFront(struct PlayList* p_playList) {
    assert(p_playList != NULL && "PlayList_deleteFromFront(): p_playList is NULL");
    assert(p_playList->p_first != NULL && "PlayList_deleteFromFront(): p_playList is empty");
    // todo
   
    p_playList -> p_first = p_playList -> p_first -> p_next;
    p_playList -> p_first -> p_next -> p_previous = NULL;
}

bool PlayList_deleteSong(struct PlayList* p_playList, const char* songName) {
    assert(p_playList != NULL && "PlayList_deleteSong(): p_playList is NULL");
    // todo
    return false;
}

void PlayList_print(const struct PlayList* p_playList) {
    assert(p_playList != NULL && "PlayList_print(): p_playList is NULL");
    struct Node* p_current = p_playList->p_first;
    while (p_current != NULL) {
        printf("%s\n", p_current->songName);
        p_current = p_current->p_next;
    }
}

void PlayList_sort(struct PlayList* p_playList) {
    assert(p_playList != NULL && "PlayList_sort(): p_playList is NULL");
    // todo
}

void PlayList_deleteAll(struct PlayList* p_playList) {
    assert(p_playList != NULL && "PlayList_deleteAll(): p_playList is NULL");
    // todo
}

char* PlayList_playingSong(const struct PlayList* p_playList) {
    assert(p_playList != NULL && "PlayList_playingSong(): p_playList is NULL");
    // todo
    return NULL;
}

void PlayList_playNext(struct PlayList* p_playList) {
    assert(p_playList != NULL && "PlayList_playNext(): p_playList is NULL");
    // todo
}

void PlayList_playPrevious(struct PlayList* p_playList) {
    assert(p_playList != NULL && "PlayList_playPrevious(): p_playList is NULL");
    // todo
}

void PlayList_free(struct PlayList** p_p_playList) {
    assert(p_p_playList != NULL && "PlayList_free(): p_p_playList is NULL");
    // todo
}