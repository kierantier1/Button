package gdx.button;
// A simple goal here. I will turn the "badLogic" dude into a Sprite.
// I will then hit-test it - changing the background colour if I hit it.
// I will then use the directional listeners to move it around.
/* First, I switched the order. I got the sprite to move, and then I tried hit testing.
Here's the problem: The Sprite's coordinates have the point of origin in the bottom left, and
the mouse gets its coordinates from the upper left. As noted here:
https://stackoverflow.com/questions/16514152/libgdx-coordinate-system-differences-between-rendering-and-touch-input
we could simply " just subtract the y from the screen height. That's a bit of a hack. ", or we can fix it with the camera settings.
So far, I did not use a camera in this lesson, so I will do so now.
Here's the foreshadowing part - how will adjusting the camera impact a tiled map, or how box2d behaves? Don't know.
Solve one problem, create a few more???

 */

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GDXButton extends ApplicationAdapter implements InputProcessor {
	SpriteBatch batch;
	Sprite sprDude;
	Texture txDude;
	int nR, nB, nG;
	OrthographicCamera oc;
	
	@Override
	public void create () {
		oc = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		oc.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		// "true" sets the camera point of origin to the upper left. But now BodLogic is upside down. See setFlip below.
		oc.update();
		nR = 1;
		nG = 0;
		nB = 0;
		batch = new SpriteBatch();
		txDude = new Texture("badlogic.jpg");
		sprDude = new Sprite(txDude);
		sprDude.setFlip(false, true); // since the camera is adjusted, we need to flip the Sprite.
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(nR, nG, nB, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.setProjectionMatrix(oc.combined);
		sprDude.draw(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		txDude.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		if(keycode== Input.Keys.DPAD_UP){
			sprDude.setY(sprDude.getY()-10f);
		}
		if(keycode== Input.Keys.DPAD_DOWN){
			sprDude.setY(sprDude.getY()+10f);
		}
		if(keycode== Input.Keys.DPAD_LEFT){
			sprDude.setX(sprDude.getX()-10f);
		}
		if(keycode== Input.Keys.DPAD_RIGHT){
			sprDude.setX(sprDude.getX()+10f);
		}
		return false;
	}
	public boolean isHit(int nX, int nY){
		System.out.println("got there");
		if(nX>sprDude.getX() && nX <sprDude.getX()+sprDude.getWidth()&& nY>sprDude.getY() && nY<sprDude.getY()+sprDude.getHeight()){
			System.out.println("hit");
			return true;
		}
		else{
			return false;
		}

	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (button == Input.Buttons.LEFT) {
			System.out.println(screenX +" " + screenY);
			if (isHit(screenX, screenY)) {
				nR = 0;
				nB = 1;
				System.out.println("bad logic!");

			} else {
				nR = 1;
				nB = 0;
			}
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
