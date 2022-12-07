
export default function getItemsInStack(s: string) {
    const object: { [index:number]: string } = {}
    let currStack = 1;
    let chars: string[] = [...s];
    for (let i = 0; i < chars.length; i+=3) {
        const slice = chars.slice(i, i+4);
        i++;
        if (slice[1] != ' ') {
            object[currStack] = slice[1];
        }
        currStack++;
    }
    return object;
}
