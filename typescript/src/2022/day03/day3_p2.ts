import fs from "fs";
import {join} from "path";

const fileName = 'input.txt'
const result = fs.readFileSync(join(__dirname, fileName), 'utf-8');
const lines = result.split(/[\n]/);
const prioritiesMap = {};

function findCommonChar(compartment1: string, compartment2: string) {
    const map = {};
    for (const compartment1Element of compartment1) {
        map[compartment1Element] = compartment1Element;
    }
    const commonChars = []
    for (const compartment2Element of compartment2) {
        if (map[compartment2Element]) {
            commonChars.push(compartment2Element);
        }
    }
    return commonChars;
}

for (let c = 0; c < 26; c++) {
    prioritiesMap[String.fromCharCode('a'.charCodeAt(0) + c)] = c+1;
}
for (let c = 0; c < 26; c++) {
    prioritiesMap[String.fromCharCode('A'.charCodeAt(0) + c)] = c+1+26;
}

let totalPriority = 0;
let groupLines = [];
for (const i in lines) {
    const line = lines[i];
    if (groupLines.length == 3) {
        const commonChars = findCommonChar(groupLines[0], groupLines[1]);
        const commonCharsUnique = findCommonChar(commonChars.join(''), groupLines[2]);
        console.log(`${commonCharsUnique[0]}`);
        totalPriority += prioritiesMap[commonCharsUnique[0]];
        groupLines = [];
    }
    groupLines.push(line);
    // totalPriority += prioritiesMap[commonChar];
    // console.log(`line (${line}) [${compartment1} | ${compartment2}] common = ${commonChar} ${prioritiesMap[commonChar]}`);
}
if (groupLines.length == 3) {
    const commonChars = findCommonChar(groupLines[0], groupLines[1]);
    const commonCharsUnique = findCommonChar(commonChars.join(''), groupLines[2]);
    totalPriority += prioritiesMap[commonCharsUnique[0]];
    console.log(`${commonCharsUnique[0]}`);
}
console.log(`Sum ${totalPriority}`);
