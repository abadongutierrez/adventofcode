import fs from "fs";
import {join} from "path";
import getItemsInStack from "./stack_parser";

const fileName = 'input.txt'
const result = fs.readFileSync(join(__dirname, fileName), 'utf-8');
const lines = result.split(/[\n]/);

let parsingStack = true;

function areItemsNumbers(items: { [p: number]: string }) {
    for (const key in items) {
        if (Number.isNaN(parseInt(items[key]))) {
            return false;
        }
    }
    return true;
}

const stack: { [p: number]: string[] } = {}

function accumulateItems(items: { [p: number]: string }) {
    for (const key in items) {
        if (stack[key]) {
            const currItems = stack[key];
            currItems.unshift(items[key]);
            stack[key] = currItems;
        } else {
            stack[key] = [items[key]];
        }
    }
}

for (const index in lines) {
    const line = lines[index];
    if (line.trim().length != 0) {
        if (parsingStack) {
            const items = getItemsInStack(line);
            if (areItemsNumbers(items)) {
                parsingStack = false;
            } else {
                accumulateItems(items);
            }
        } else {
            const split = line.split(" ");
            const moveTotal = parseInt(split[1]);
            const fromStack = parseInt(split[3]);
            const toStack = parseInt(split[5]);
            const acc = [];
            for (let i = 0; i < moveTotal; i++) {
                const item = stack[fromStack].pop();
                acc.unshift(item);
            }
            for (const accElement of acc) {
                if (accElement) stack[toStack].push(accElement);
            }
        }
    }
}

const final = [];
for (const key in stack) {
    final.push(stack[key][stack[key].length - 1]);
}

console.log(final.join(''));
