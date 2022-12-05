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
    for (const compartment2Element of compartment2) {
        if (map[compartment2Element]) {
            return compartment2Element;
        }
    }
    return null
}

for (let c = 0; c < 26; c++) {
    prioritiesMap[String.fromCharCode('a'.charCodeAt(0) + c)] = c+1;
}
for (let c = 0; c < 26; c++) {
    prioritiesMap[String.fromCharCode('A'.charCodeAt(0) + c)] = c+1+26;
}

let totalPriority = 0;
for (const i in lines) {
    const line = lines[i];
    const compartment1 = line.slice(0, line.length/2);
    const compartment2 = line.slice(line.length/2);
    const commonChar = findCommonChar(compartment1, compartment2);
    totalPriority += prioritiesMap[commonChar];
    // console.log(`line (${line}) [${compartment1} | ${compartment2}] common = ${commonChar} ${prioritiesMap[commonChar]}`);
}
console.log(`Sum ${totalPriority}`);
