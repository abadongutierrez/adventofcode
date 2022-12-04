import * as fs from 'fs';
import { join } from 'path';

const fileName = 'input.txt'
const result = fs.readFileSync(join(__dirname, fileName), 'utf-8');
const lines = result.split(/[\n]/);
const elfCounts = [];
let currElf = 0;
let caloriesCont = 0;
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

console.log(`The Elf with most calories (${Math.max(...elfCounts)})`);
