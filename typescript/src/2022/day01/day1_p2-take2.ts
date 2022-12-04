import * as fs from 'fs';
import { join } from 'path';

const fileName = 'input.txt'
const result = fs.readFileSync(join(__dirname, fileName), 'utf-8');
const lines = result.split(/[\n]/);
const elfCounts: number[] = [];
let caloriesCont = 0;
let currElf = 0;

for (const index in lines) {
    const line = lines[index];
    if (line.trim().length <= 0) {
        elfCounts[currElf] = caloriesCont;
        caloriesCont = 0;
        currElf++;
    } else {
        const lineAsNum = parseInt(line);
        caloriesCont += lineAsNum;
    }
}
// finished processing the lines, but what if there is still an Elf to process?
// last line in file wasn't a blank line?
if (lines[lines.length - 1].trim().length != 0) {
    elfCounts[currElf] = caloriesCont;
}
const top3Sum = elfCounts.sort((n1, n2) => n2 - n1).slice(0, 3).reduce((acc, curr) => acc + curr, 0);

console.log(`The calories total of the Top 3 ${top3Sum}`);
