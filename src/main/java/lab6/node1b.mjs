import waterfall from "async/waterfall.js";


function printAsync(s, cb) {
   var delay = Math.floor((Math.random()*1000)+500);
   setTimeout(function() {
       console.log(s);
       if (cb) cb();
   }, delay);
}

function task1(cb) {
    printAsync("1", function() {
        task2(cb);
    });
}

function task2(cb) {
    printAsync("2", function() {
        task3(cb);
    });
}

function task3(cb) {
    printAsync("3", cb);
}

// wywolanie sekwencji zadan
// task1(function() {
//     console.log('done!');
// });


/* 
** Zadanie:
** Napisz funkcje loop(n), ktora powoduje wykonanie powyzszej 
** sekwencji zadan n razy. Czyli: 1 2 3 1 2 3 1 2 3 ... done
** 
*/

// function loop1(n) {
//     var delay = 5000;
//     for (let i = 0; i < n - 1; i++) {
//         // task1();
//         setTimeout(function() {
//             task1();
//         }, delay * i);
//       }

//     setTimeout(function() {
//         task1(function() {
//             console.log('done!');
//         });
//     }, delay * n);
// }

function loop2(n) {
    var tasks = [];
    for (let i = 0; i < n; i++) {
        tasks.push(task1);
    }

    waterfall(tasks);
}



// loop1(3);
loop2(3);