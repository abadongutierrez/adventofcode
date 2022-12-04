import * as fs from 'fs';
import { join } from 'path';

const fileName = 'input.txt'
const result = fs.readFileSync(join(__dirname, fileName), 'utf-8');
const lines = result.split(/[\n]/);
let maxCalories = [0, 0, 0];
let caloriesCont = 0;
let currElf = 1;
let top3Index = 0;

function setTop3IfAny() {
    // first set all 3 top with the first 3 values
    if (top3Index <= 2) {
        maxCalories[top3Index] = caloriesCont;
        maxCalories = maxCalories.sort((n1, n2) => n1 - n2);
        top3Index++;
    } else {
        for (const i in maxCalories) {
            if (caloriesCont > maxCalories[i]) {
                maxCalories[i] = caloriesCont;
                maxCalories = maxCalories.sort((n1, n2) => n1 - n2);
                break;
            }
        }
    }
}

for (const index in lines) {
    const line = lines[index];
    if (line.trim().length <= 0) {
        // first we need to fill the top 3 array
        setTop3IfAny();
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
    setTop3IfAny();
}

const top3Sum = maxCalories.reduce((acc, curr) => acc + curr, 0);

console.log(`The calories total of the Top 3 ${top3Sum}`);
