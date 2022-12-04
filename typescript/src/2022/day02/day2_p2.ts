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
const roundNeedsToEnd = {
    'X': 'lost', 'Y': 'draw', 'Z': 'win',
}

function getShape(c1Shape: string, outcome: string) {
    if (c1Shape == 'rock') {
        if (outcome == 'win') return 'paper';
        if (outcome == 'lost') return 'scissors';
    }

    if (c1Shape == 'paper') {
        if (outcome == 'lost') return 'rock';
        if (outcome == 'win') return 'scissors';
    }

    if (c1Shape == 'scissors') {
        if (outcome == 'win') return 'rock';
        if (outcome == 'lost') return 'paper';
    }

    return c1Shape;
}

for (const i in lines) {
    const line = lines[i];
    const c1Shape = shapes[line[0]];
    const outcome = roundNeedsToEnd[line[2]];
    const c2Shape = getShape(c1Shape, outcome);
    score += shapeValue[c2Shape] + outcomeValue[outcome];
}

console.log(`Total score ${score}`);
