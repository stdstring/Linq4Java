package IterableExtensionImpl;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author std_string
 */
enum IteratorState {
    // итератор находится в неизвесном состоянии (сразу после выборки очередного элемента)
    UNKNOWN,
    // итератор находится в активном состоянии (очередной элемент есть)
    ACTIVE,
    // итератор находится в конечном состоянии (очередного элемента нет)
    FINISHED
}
