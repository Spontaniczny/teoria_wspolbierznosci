
var Fork = function() {
    this.state = 0;
    return this;
}



Fork.prototype.acquire = function(cb) { 
    // zaimplementuj funkcje acquire, tak by korzystala z algorytmu BEB
    // (http://pl.wikipedia.org/wiki/Binary_Exponential_Backoff), tzn:
    // 1. przed pierwsza proba podniesienia widelca Filozof odczekuje 1ms
    // 2. gdy proba jest nieudana, zwieksza czas oczekiwania dwukrotnie
    //    i ponawia probe itd.

    var rec = function(fork, wait_time, cb)
    {
        setTimeout(function(){
            if (fork.state == 1) // podniesiony
            {
                // console.log("Waiting " + wait_time);
                rec(fork, wait_time * 2, cb);
            }
            else  // opuszczony
            {
                fork.state = 1;

                if (cb) { cb() }
            }
        }, wait_time);
    }

    rec(this, 1, cb);
}

Fork.prototype.release = function() { 
    this.state = 0; 
}

var Philosopher = function(id, forks) {
    this.id = id;
    this.forks = forks;
    this.f1 = id % forks.length;
    this.f2 = (id+1) % forks.length;
    return this;
}

Philosopher.prototype.startNaive = function(count) {
    var forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;
    
    // zaimplementuj rozwiazanie naiwne
    // kazdy filozof powinien 'count' razy wykonywac cykl
    // podnoszenia widelcow -- jedzenia -- zwalniania widelcow

    var left_fork = forks[f1];
    var right_fork = forks[f2];

    var loop = function(count){
        if (count == 0)
        {
            return;
        }

        right_fork.acquire(function(){
            console.log(id + " taking right fork");

            left_fork.acquire(function(){
                console.log(id + " taking left fork");

                setTimeout(function(){
                    left_fork.release();
                    right_fork.release();
    
                    setTimeout(function(){
                            loop(count - 1);
                    }, Math.floor((Math.random()*100)+500));
                }, Math.floor((Math.random()*100)+500));
            });
        });
    };

    loop(count);
}

Philosopher.prototype.startAsym = function(count) {
    var forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;
    
    // zaimplementuj rozwiazanie asymetryczne
    // kazdy filozof powinien 'count' razy wykonywac cykl
    // podnoszenia widelcow -- jedzenia -- zwalniania widelcow

    if (id % 2 == 0)
    {
        var first = forks[f2];
        var second = forks[f1];
    }
    else
    {
        var first = forks[f1];
        var second = forks[f2];
    }

    var loop = function(count){
        if (count == 0)
        {
            return;
        }

        console.time(id);
        first.acquire(function(){
            //console.log(id + " taking first fork");

            second.acquire(function(){
                //console.log(id + " taking second fork");
                console.timeEnd(id);
                setTimeout(function(){
                    first.release();
                    second.release();
                    //console.log(id + " released");
    
                    setTimeout(function(){
                        loop(count - 1);
                    }, Math.floor((Math.random()*100)+500));
                }, Math.floor((Math.random()*100)+500));
            });
        });
    };

    loop(count);
}

var Conductor = function(N){
    this.state = N - 1;
    return this;
}

Conductor.prototype.acquire = function(cb){
    var rec = function(conductor, wait_time, cb)
    {
        setTimeout(function(){
            if (conductor.state > 0)
            {
                conductor.state = conductor.state - 1;

                if (cb)
                {
                    cb();
                }
            }
            else
            {
                rec(conductor, wait_time * 2, cb);
            }
        }, wait_time);
    }

    rec(this, 1, cb);
}

Conductor.prototype.release = function(){
    this.state = this.state + 1;
}

Philosopher.prototype.startConductor = function(count) {
    var forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;
    
    // zaimplementuj rozwiazanie z kelnerem
    // kazdy filozof powinien 'count' razy wykonywac cykl
    // podnoszenia widelcow -- jedzenia -- zwalniania widelcow

    var left_fork = forks[f1];
    var right_fork = forks[f2];

    var loop = function(count){
        if (count == 0)
        {
            return;
        }

        console.time(id);
        conductor.acquire(function(){
            right_fork.acquire(function(){
                //console.log(id + " taking right fork");

                left_fork.acquire(function(){
                    //console.log(id + " taking left fork");
                    console.timeEnd(id);
                    setTimeout(function(){
                        left_fork.release();
                        right_fork.release();
                        conductor.release();
                        //console.log(id + " released");
                        setTimeout(function(){
                            loop(count - 1);
                        }, Math.floor((Math.random()*100)+500));
                    }, Math.floor((Math.random()*100)+500));
                });
            });
        });
    };

    loop(count);
}


var N = 5;
var forks = [];
var philosophers = []
for (var i = 0; i < N; i++) {
    forks.push(new Fork());
}

for (var i = 0; i < N; i++) {
    philosophers.push(new Philosopher(i, forks));
}

conductor = new Conductor(N);

for (var i = 0; i < N; i++) {
//    philosophers[i].startNaive(10);
//    philosophers[i].startAsym(10);0
   philosophers[i].startConductor(10);
}