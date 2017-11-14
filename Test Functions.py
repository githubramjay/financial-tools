# limit order book (lob): list of dictionaries of instrument
# dict (type, price, size, status, time)
# obviously not a hpc architecture
# R. Jayaraman

import math


class Time(object):         # parses strings into arithmetically enabled time object
    def __init__(self, _string=None):
        units = _string.split('.')
        self.hour = int(units[0])
        self.minute = int(units[1])
        self.second = int(units[2])
        self.m_second = int(units[3])
        self.mu_second = int(units[4])

    def minus(self, time=None):
        agg_self = self.mu_second + (self.m_second * 1000) + (self.second * 1000 * 1000) + (self.minute * 1000 * 1000 * 60) + (self.hour * 1000 * 1000 * 60 * 60)
        agg_time = time.mu_second + (time.m_second * 1000) + (time.second * 1000 * 1000) + (time.minute * 1000 * 1000 * 60) + (time.hour * 1000 * 1000 * 60 * 60)
        timedelta = agg_self - agg_time
        return self._mu_to_time(timedelta)

    def plus(self, time=None):
        agg_mu_seconds = self.mu_second + time.mu_second
        plus_m_seconds = math.floor(agg_mu_seconds / 1000)
        _mu_seconds = int(agg_mu_seconds - (1000 * plus_m_seconds))

        agg_m_seconds = self.m_second + time.m_second + plus_m_seconds
        plus_seconds = math.floor(agg_m_seconds / 1000)
        _m_seconds = int(agg_m_seconds - (1000 * plus_seconds))

        agg_seconds = self.second + time.second + plus_seconds
        plus_minutes = math.floor(agg_seconds / 60)
        _seconds = int(agg_seconds - (60 * plus_minutes))

        agg_minutes = self.minute + time.minute + plus_minutes
        plus_hours = math.floor(agg_minutes / 60)
        _minutes = int(agg_minutes - (60 * plus_hours))

        agg_hours = self.hour + time.hour + plus_hours
        lap = math.floor(agg_hours / 24)
        _hours = int(agg_hours - (24 * lap))

        return Time(str(_hours)+'.'+str(_minutes)+'.'+str(_seconds)+'.'
            +str(_m_seconds)+'.'+str(_mu_seconds))

    def _mu_to_time(self, micro=None):
        millimult = 1000
        secmult = 1000 * 1000
        minmult = 1000 * 1000 * 60
        hourmult = 1000 * 1000 * 60 * 60
        remain = 0

        hours = int(math.floor(micro / hourmult))
        remain = micro % hourmult
        mins = int(math.floor(remain / minmult))
        remain %= minmult
        secs = int(math.floor(remain / secmult))
        remain %= secmult
        millis = int(math.floor(remain / millimult))
        micros = remain % millimult
        return Time(str(hours)+'.'+str(mins)+'.'+str(secs)+'.'+str(millis)+'.'+str(micros))

    def __str__(self):
        return str(self.hour)+'.'+str(self.minute)+'.'+str(self.second)+'.'+str(self.m_second)+'.'+str(self.mu_second)


def safeguard(lob=None):    # checks validity of lob
    if lob is None:
        raise ValueError("Limit Order Book is empty!")
    elif not isinstance(lob, list):
        raise ValueError("Limit Order Book is not a list!")
    for i in lob:
        if len(i.keys()) != 5:
            raise ValueError("Orders are not structured properly!")
        try:
            temp = i['type']
            temp = i['price']
            temp = i['size']
            temp = i['status']
            temp = i['time']
        except:
            raise ValueError("Orders are not structured properly!")
        if not ((i['type'] == 'b' or i['type'] == 's') and (i['price'] >= 0 and isinstance(i['price'], float)) and
            (i['size'] > 0 and isinstance(i['size'], float)) and (i['status'] == 'e' or i['status'] == 'p' or
                i['status'] == 'f') and (isinstance(i['time'], Time))):
            raise ValueError("Entries per order have improper schema!")


def valid(t=None):  # checks validity of time objects
    if (t.hour < 0 or t.hour > 23) or (t.minute < 60 or t.minute > 59) or (t.second < 0 or t.second > 59) or (t.m_second < 0 or t.m_second > 999) or (t.mu_second < 0 or t.mu_second > 999):
        raise ValueError("Time is improperly formatted!")
    if isinstance(t.hour, int) and isinstance(t.minute, int) and isinstance(t.second, int) and isinstance(t.m_second, int) and isinstance(t.mu_second, int):
        pass
    else:
        raise ValueError("Time is improperly formatted!")


def vwap(lob=None): # volume-weighted average price
    _sum = sum([i['price']*i['size'] for i in lob])
    total = sum([i['size'] for i in lob])
    return _sum/total


def pairwise_time(lob=None):    # price delta per time delta
    times = [i['time'] for i in lob]
    prices = [i['price'] for i in lob]
    pairwise = list(zip(tuple(zip(times[:], times[1:])), (tuple(zip(prices[:], prices[1:])))))
    delta = []
    for i in pairwise:
        inner = []
        inner.append(i[0][1].minus(i[0][0]))
        inner.append(i[1][1] - i[1][0])
        delta.append(inner)
    return delta


def read(path=None):    # reads and parses from file
    lob = []
    app = open(path, 'r')
    for j in app:
        i = j.split('|')
        lob.append({'type': (i[1]), 'price': float(i[2]), 'size': float(i[3]), 'status': (i[4]), 'time': Time(str(i[5]))})
    return lob


def write(path=None):   # writes to file
    with open(path, 'w') as _file:
        for i in _file:
            pass


def tests():
    # testing lob building
    order_book = list()
    order_book2 = list()
    order_book.append({'type': 'b', 'price': 10.00, 'size': 17.0, 'status': 'e', 'time': Time('10.30.40.198.000')})
    order_book.append({'type': 's', 'price': 12.10, 'size': 93.0, 'status': 'f', 'time': Time('10.30.41.198.000')})
    order_book.append({'type': 's', 'price': 8.23, 'size': 600.0, 'status': 'p', 'time': Time('10.30.43.198.000')})

    # testing lob safeguard
    safeguard(order_book)
    try:
        order_book2.append({'type': 'a', 'price': 8.23, 'size': 610.0, 'status': '8', 'time': Time('10.30.43.198.000')})
        safeguard(order_book2)
    except ValueError:
        print("caught")

    print(order_book) # standard list/dict print
    print(vwap(order_book))  # testing vwap
    print(pairwise_time(order_book))  # testing pairwise time

    # testing time arithmetic
    a = Time('10.59.05.000.100')
    b = Time('00.00.00.000.900')
    c = Time('32.52.12.523.152')
    print(a)
    print(b)
    print(a.plus(b))
    print(a.minus(b))

    # testing time safeguard
    try:
        valid(c)
    except ValueError:
        print("caught")

    # test read/write
    print(read('Python Files/FD/test_file.lob'))


if __name__ == "__main__":
    tests()
