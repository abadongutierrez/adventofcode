import * as fs from 'fs';
import { join } from 'path';

const fileName = 'input.txt'
const result = fs.readFileSync(join(__dirname, fileName), 'utf-8');
const lines = result.split(/[\n]/);
let maxCalories = 0;
let maxElf = 1;
let caloriesCont = 0;
let currElf = 1;

function setMaxIfAny() {
    if (caloriesCont > maxCalories) {
        maxCalories = caloriesCont;
        maxElf = currElf;
    }
}

for (const index in lines) {
    const line = lines[index];
    if (line.trim().length <= 0) {
        setMaxIfAny();
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
    setMaxIfAny();
}

console.log(`The Elf with most calories (${maxCalories}) is ${maxElf}`);
