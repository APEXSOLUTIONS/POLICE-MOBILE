/***
 * 
 * Copyright (C) 2008 Alessandro La Rosa
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * Contact: alessandro.larosa@gmail.com
 *
 * Author: Alessandro La Rosa
 */
import java.util.Timer;
import java.util.TimerTask;

import javax.microedition.lcdui.Graphics;

public class LoadingBar
{
	public long stepInterval = 250L;
	
	public int width = 0;
	public int height = 0;
	
	int padding = 0;
	
	int color = 0x000000;
	
	int squares = 0;
	int squareWidth = 0;
	
	int currentSquares = 0;
	
	Timer stepTimer = null;
	
	public LoadingBar(int width, int height, int padding, int squares, int color)
	{
		this.width = width;
		this.height = height;
		this.squares = squares;
		
		this.color = color;
		this.padding = padding;
		
		this.squareWidth = (width - padding) / (squares) - padding;
	}
	public void paint(Graphics g)
	{
		g.setColor(color);
		
		for(int i = 0; i < currentSquares; i++)
		{
			g.fillRect(i * (squareWidth + padding), 0, squareWidth, height);
		}
	}
	public void start()
	{
		stepTimer = new Timer();
		
		stepTimer.schedule(new TimerTask() 
			{
				public void run()
				{
					step();
				}
			},
			stepInterval, stepInterval
		);
	}
	public void stop()
	{
		stepTimer.cancel();
	}
	void step()
	{
		currentSquares = (currentSquares + 1) % (squares + 1);
	}
}