import fs from "fs";
import {join} from "path";

const fileName = 'input.txt'
const result = fs.readFileSync(join(__dirname, fileName), 'utf-8');
const lines = result.split(/[\n]/);
let score = 0;
const shapes = {
    'A': 'rock', 'B': 'paper', 'C': 'scissors',
    'X': 'rock', 'Y': 'paper', 'Z': 'scissors',
}
const shapeValue = {
    'rock': 1, 'paper': 2, 'scissors': 3,
}
const outcomeValue = {
    'win': 6, 'draw': 3, 'lost': 0,
}

function getOutcome(c1Shape: string, c2Shape: string) {
    if (c1Shape == 'rock') {
        if (c2Shape == 'paper') return 'win';
        if (c2Shape == 'scissors') return 'lost';
    }

    if (c1Shape == 'paper') {
        if (c2Shape == 'rock') return 'lost';
        if (c2Shape == 'scissors') return 'win';
    }

    if (c1Shape == 'scissors') {
        if (c2Shape == 'rock') return 'win';
        if (c2Shape == 'paper') return 'lost';
    }

    return 'draw';
}

for (const i in lines) {
    const line = lines[i];
    const c1Shape = shapes[line[0]];
    const c2Shape = shapes[line[2]];
    const outcome = getOutcome(c1Shape, c2Shape);
    score += shapeValue[c2Shape] + outcomeValue[outcome];
}

console.log(`Total score ${score}`);
