package modules.terrain;

import editor.tools.terrainEditor.TerrainEditorInterface;
import engine.math.Vec2f;
import engine.scenegraph.Node;

public class TerrainQuadtree extends Node{
	
	private static int rootPatches = 10;
		
	public TerrainQuadtree(TerrainConfiguration terrConfig){
		init(terrConfig);
	}	
	
	public void init(TerrainConfiguration terrConfig){
		
		for (int i=0; i<rootPatches; i++){
			for (int j=0; j<rootPatches; j++){
				
				addChild(new TerrainNode(terrConfig, new Vec2f(i/(float)rootPatches,j/(float)rootPatches), 0, new Vec2f(i,j)));
			}
		}
	}
	
	public void update()
	{
		TerrainEditorInterface.updateLoDPatchesChart();
		
		super.update();
	}

	public static int getRootPatches() {
		return rootPatches;
	}
}
