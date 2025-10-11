
// PLAYLIST.h

#ifndef PLAYLIST_H
#define PLAYLIST_H

#include <stdbool.h>

#define MAX_NAME_LENGTH 30

struct Node {
    char songName[MAX_NAME_LENGTH + 1];
    struct Node* p_previous;
    struct Node* p_next;
};

struct PlayList {
    struct Node* p_first;
    struct Node* p_playing;
};

struct PlayList* PlayList_new();
void PlayList_insertAtFront(struct PlayList* p_playList, const char* songName);
void PlayList_insertInOrder(struct PlayList* p_playList, const char* songName);
void PlayList_insertAtEnd(struct PlayList* p_playList, const char* songName);
bool PlayList_insertAfter(struct PlayList* p_playList, const char* targetSong, const char* newSong);
bool PlayList_insertBefore(struct PlayList* p_playList, const char* targetSong, const char* newSong);
void PlayList_deleteFromFront(struct PlayList* p_playList);
bool PlayList_deleteSong(struct PlayList* p_playList, const char* songName);
void PlayList_print(const struct PlayList* p_playList);
void PlayList_sort(struct PlayList* p_playList);
void PlayList_deleteAll(struct PlayList* p_playList);
char* PlayList_playingSong(const struct PlayList* p_playList);
void PlayList_playNext(struct PlayList* p_playList);
void PlayList_playPrevious(struct PlayList* p_playList);
void PlayList_free(struct PlayList** p_p_playList);

#endif // PLAYLIST_H
