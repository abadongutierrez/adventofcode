import fs from "fs";
import {join} from "path";

const fileName = 'input.txt'
const result = fs.readFileSync(join(__dirname, fileName), 'utf-8');
const lines = result.split(/[\n]/);

function extractRange(line: string, rangeStr: string) {
    const numbersStr = rangeStr.split('-')
    return [parseInt(numbersStr[0]), parseInt(numbersStr[1])];
}

function isOverlaping(range1: number[], range2: number[]) {
    return (range2[0] >= range1[0] && range2[0] <= range1[1]) || (range2[1] >= range1[0] && range2[1] <= range1[1]);
}

let containCount = 0;

for (const i in lines) {
    const line = lines[i];
    const range1 = extractRange(line, line.split(',')[0]);
    const range2 = extractRange(line, line.split(',')[1]);
    containCount += (isOverlaping(range1, range2) || isOverlaping(range2, range1) ? 1 : 0);
}

console.log(`${containCount}`);
