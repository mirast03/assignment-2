"""Draw plots from measured CSV only. Requires Pillow (python -m pip install Pillow)."""
import csv
import math
from pathlib import Path
from PIL import Image, ImageDraw, ImageFont

ROOT = Path(__file__).resolve().parents[1]
rows = list(csv.DictReader((ROOT / 'results/tables/summary.csv').open()))
works = ['RandomAccess', 'Search', 'InsertFront', 'RemoveFront', 'InsertMiddle', 'RemoveMiddle', 'PriorityInsert', 'PriorityExtract']
labels = ['Random access (10,000 gets)', 'Search (1,000 queries)', 'Insert at index 0', 'Remove at index 0', 'Insert at fixed n/2', 'Remove at fixed n/2', 'Heap: insert n values', 'Heap: extract n values']
colors = {'DynamicArray': '#2563eb', 'LinkedList': '#db5b21', 'MinHeap': '#15803d'}
font_paths = ['/System/Library/Fonts/Supplemental/Arial.ttf', '/usr/share/fonts/truetype/dejavu/DejaVuSans.ttf']
def font(size):
    for path in font_paths:
        if Path(path).exists():
            return ImageFont.truetype(path, size)
    return ImageFont.load_default()

def metric(row):
    work = row['workload']
    if work == 'RandomAccess': return float(row['mean_accesses'])
    if work == 'Search' or work.startswith('Priority'): return float(row['mean_comparisons'])
    return float(row['mean_movements']) + float(row['mean_accesses'])

for is_time, filename in [(True, 'execution-time.png'), (False, 'logical-work.png')]:
    img = Image.new('RGB', (1800, 1590), '#ffffff')
    d = ImageDraw.Draw(img)
    d.text((45, 24), 'Execution time vs. n' if is_time else 'Comparisons / accesses / movements vs. n', fill='#172033', font=font(34))
    d.text((45, 72), 'Measured means of 5 runs; n on log scale. ' + ('Time in ms on log scale.' if is_time else 'Count axis uses log10(count + 1), including zero.'), fill='#475569', font=font(20))
    for k, (work, label) in enumerate(zip(works, labels)):
        ox = 45 + (k % 2) * 885
        oy = 120 + (k // 2) * 348
        d.text((ox, oy), label, fill='#172033', font=font(23))
        selected = [r for r in rows if r['workload'] == work]
        vals = [float(r['mean_time_ns']) / 1e6 if is_time else metric(r) for r in selected]
        transform = math.log10 if is_time else lambda v: math.log10(v + 1)
        lo = math.floor(min(map(transform, vals))) if is_time else 0
        hi = math.ceil(max(map(transform, vals)))
        if hi <= lo: hi = lo + 1
        left, top, width, height = ox + 90, oy + 52, 700, 222
        def point(n, v): return (left + (math.log10(n) - 2) / 3 * width, top + (hi - transform(v)) / (hi - lo) * height)
        ticks = list(range(lo, hi + 1))
        if len(ticks) > 7: ticks = ticks[::2] + ([hi] if ticks[-1] != hi else [])
        for t in ticks:
            y = top + (hi - t) / (hi - lo) * height
            d.line((left, y, left + width, y), fill='#e2e8f0', width=1)
            tick = f'{10**t:g}' if is_time else ('0' if t == 0 else f'{10**t - 1:,}')
            d.text((ox, y - 8), tick, fill='#475569', font=font(15))
        d.line((left, top, left, top + height, left + width, top + height), fill='#64748b', width=2)
        for n in [100, 1000, 10000, 100000]:
            x = point(n, vals[0])[0]
            d.text((x - 24, top + height + 8), f'{n:,}', fill='#475569', font=font(16))
        for j, structure in enumerate(dict.fromkeys(r['structure'] for r in selected)):
            series = [r for r in selected if r['structure'] == structure]
            points = [point(int(r['n']), float(r['mean_time_ns']) / 1e6 if is_time else metric(r)) for r in series]
            d.line(points, fill=colors[structure], width=3)
            for x, y in points: d.ellipse((x - 5, y - 5, x + 5, y + 5), fill=colors[structure])
            d.text((left + j * 220, oy + 316), structure, fill=colors[structure], font=font(17))
    d.text((45, 1538), 'Removal uses m=min(1,000, n-index); see README. Mutation metric = movements + node/value accesses.', fill='#475569', font=font(18))
    img.save(ROOT / 'results/plots' / filename)

with (ROOT / 'results/tables/results.md').open('w') as out:
    out.write('# Measured results\n\nTimes are arithmetic means of five measured runs. Raw times are in raw.csv.\n\n')
    for work, label in zip(works, labels):
        out.write(f'## {label}\n\n| Structure | n | Actual m | Mean time (ms) | Accesses | Comparisons | Movements | Workload complexity |\n|---|---:|---:|---:|---:|---:|---:|---|\n')
        for r in rows:
            if r['workload'] == work:
                out.write(f"| {r['structure']} | {int(r['n']):,} | {r['m']} | {float(r['mean_time_ns']) / 1e6:.6f} | {float(r['mean_accesses']):,.0f} | {float(r['mean_comparisons']):,.0f} | {float(r['mean_movements']):,.0f} | {r['theory']} |\n")
        out.write('\n')
print('Created two plots and results.md from summary.csv')
