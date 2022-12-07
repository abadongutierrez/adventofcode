import {expect} from "@jest/globals";
import getItemsInStack from "./stack_parser";

describe('Test parsing stack', () => {
    test('        [Q] [B]         [H]', () => {
        const itemsInStack = getItemsInStack('        [Q] [B]         [H]');
        expect(itemsInStack).toMatchObject({
            3: 'Q',
            4: 'B',
            7: 'H'
        });
    });
    test('    [F] [W] [D] [Q]     [S]', () => {
        const itemsInStack = getItemsInStack('    [F] [W] [D] [Q]     [S]');
        expect(itemsInStack).toMatchObject({
            2: 'F',
            3: 'W',
            4: 'D',
            5: 'Q',
            7: 'S'
        });
    });
    test('    [R] [D] [L] [C] [N] [Q]     [R]', () => {
        const itemsInStack = getItemsInStack('    [R] [D] [L] [C] [N] [Q]     [R]');
        expect(itemsInStack).toMatchObject({
            2: 'R',
            3: 'D',
            4: 'L',
            5: 'C',
            7: 'Q',
            9: 'R'
        });
    });
    test('[D] [P] [R] [W] [H] [R] [Z] [W] [S]', () => {
        const itemsInStack = getItemsInStack('[D] [P] [R] [W] [H] [R] [Z] [W] [S]');
        expect(itemsInStack).toMatchObject({
            1: 'D',
            2: 'P',
            3: 'R',
            4: 'W',
            5: 'H',
            6: 'R',
            7: 'Z',
            8: 'W',
            9: 'S'
        });
    });
    test(' 1   2   3   4   5   6   7   8   9 ', () => {
        const itemsInStack = getItemsInStack(' 1   2   3   4   5   6   7   8   9 ');
        expect(itemsInStack).toMatchObject({
            1: '1',
            2: '2',
            3: '3',
            4: '4',
            5: '5',
            6: '6',
            7: '7',
            8: '8',
            9: '9'
        });
    });
});
