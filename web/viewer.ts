interface FrameStats {
    fps: number;
    resolution: string;
}

class EdgeDetectionViewer {
    private canvas: HTMLCanvasElement;
    private ctx: CanvasRenderingContext2D;
    private statsElement: HTMLElement;
    private currentFPS: number = 0;

    constructor(canvasId: string, statsId: string) {
        this.canvas = document.getElementById(canvasId) as HTMLCanvasElement;
        this.ctx = this.canvas.getContext('2d')!;
        this.statsElement = document.getElementById(statsId)!;

        this.canvas.width = 640;
        this.canvas.height = 480;

        this.displaySampleFrame();
    }

    public displayFrame(base64Image: string): void {
        const img = new Image();
        img.onload = () => {
            this.ctx.clearRect(0, 0, this.canvas.width, this.canvas.height);
            this.ctx.drawImage(img, 0, 0, this.canvas.width, this.canvas.height);
            this.updateStats(this.canvas.width, this.canvas.height);
        };
        img.onerror = () => console.error('Failed to load image');
        img.src = base64Image;
    }

    private displaySampleFrame(): void {
        const sampleBase64 = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNk+M9QDwADhgGAWjR9awAAAABJRU5ErkJggg==";
        this.displayFrame(sampleBase64);
    }

    private updateStats(width: number, height: number): void {
        const stats: FrameStats = { fps: this.currentFPS, resolution: `${width}x${height}` };
        this.statsElement.innerHTML = `<strong>FPS:</strong> ${stats.fps} | <strong>Resolution:</strong> ${stats.resolution}`;
    }

    public setFPS(fps: number): void { this.currentFPS = fps }
}

document.addEventListener('DOMContentLoaded', () => {
    const viewer = new EdgeDetectionViewer('canvas', 'stats');
    viewer.setFPS(15);
});
