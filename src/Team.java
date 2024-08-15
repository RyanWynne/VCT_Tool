import java.util.Comparator;

public class Team {
	String name, region;
	float bindW, bindAtt, bindDef, bindAttSel, bindDefSel, bindGames;
	float havenW, havenAtt, havenDef, havenAttSel, havenDefSel, havenGames;
	float ascentW, ascentAtt, ascentDef, ascentAttSel, ascentDefSel, ascentGames;
	float iceboxW, iceboxAtt, iceboxDef, iceboxAttSel, iceboxDefSel, iceboxGames;
	float lotusW, lotusAtt, lotusDef, lotusAttSel, lotusDefSel, lotusGames;
	float sunsetW, sunsetAtt, sunsetDef, sunsetAttSel, sunsetDefSel, sunsetGames;
	float abyssW, abyssAtt, abyssDef, abyssAttSel, abyssDefSel, abyssGames;
	int gamesPlayed = (int)(abyssAttSel+abyssDefSel+sunsetAttSel+sunsetDefSel
			+ascentAttSel+ascentDefSel+bindAttSel+bindDefSel
			+havenAttSel+havenDefSel+iceboxAttSel+iceboxDefSel
			+lotusAttSel+lotusDefSel);
	int rating, intlRating, score, mapsWon, champsWon, seed;

	public Team() {
		
	}
	
	public Team(String name, String region, 
			float bindW, float bindAtt, float bindDef, float bindAttSel, float bindDefSel,
			float havenW, float havenAtt, float havenDef, float havenAttSel, float havenDefSel,
			float ascentW, float ascentAtt, float ascentDef, float ascentAttSel, float ascentDefSel,
			float iceboxW, float iceboxAtt, float iceboxDef, float iceboxAttSel, float iceboxDefSel,
			float lotusW, float lotusAtt, float lotusDef, float lotusAttSel, float lotusDefSel,
			float sunsetW, float sunsetAtt, float sunsetDef, float sunsetAttSel, float sunsetDefSel,
			float abyssW, float abyssAtt, float abyssDef, float abyssAttSel, float abyssDefSel,
			int rating, int intlRating) {
        this.name = name;
        this.region = region;
        this.bindW = bindW; this.bindAtt = bindAtt; this.bindDef = bindDef; 
        this.bindAttSel = bindAttSel; this.bindDefSel = bindDefSel; this.bindGames = bindAttSel+bindDefSel;
        this.havenW = havenW; this.havenAtt = havenAtt; this.havenDef = havenDef; 
        this.havenAttSel = havenAttSel; this.havenDefSel = havenDefSel; this.havenGames = havenAttSel+havenDefSel;
        this.ascentW = ascentW; this.ascentAtt = ascentAtt; this.ascentDef = ascentDef;
        this.ascentAttSel = ascentAttSel; this.ascentDefSel = ascentDefSel; this.ascentGames = ascentAttSel+ascentDefSel;
        this.iceboxW = iceboxW; this.iceboxAtt = iceboxAtt; this.iceboxDef = iceboxDef;
        this.iceboxAttSel = iceboxAttSel; this.iceboxDefSel = iceboxDefSel; this.iceboxGames = iceboxAttSel+iceboxDefSel;
        this.lotusW = lotusW; this.lotusAtt = lotusAtt; this.lotusDef = lotusDef;
        this.lotusAttSel = lotusAttSel; this.lotusDefSel = lotusDefSel; this.lotusGames = lotusAttSel+lotusDefSel;
        this.sunsetW = sunsetW; this.sunsetAtt = sunsetAtt; this.sunsetDef = sunsetDef;
        this.sunsetAttSel = sunsetAttSel; this.sunsetDefSel = sunsetDefSel; this.sunsetGames = sunsetAttSel+sunsetDefSel;
        this.abyssW = abyssW; this.abyssAtt = abyssAtt; this.abyssDef = abyssDef;
        this.abyssAttSel = abyssAttSel; this.abyssDefSel = abyssDefSel; this.abyssGames = abyssAttSel+abyssDefSel;
        this.rating = rating;
        this.intlRating = intlRating;
    }
	
	public void win(Team opposition) {
		if(opposition.rating > rating +60) {
			rating += 7;
			return;
		}
		if(opposition.rating > rating +30) {
			rating += 6;
			return;
		}
		if(opposition.rating > rating +10) {
			rating += 5;
			return;
		}
		if(opposition.rating < rating +10)
		{
			rating += 5;
			return;
		}
		if(opposition.rating < rating +20) {
			rating += 4;
			return;
		}
		if(opposition.rating < rating +30) {
			rating += 4;
			return;
		}
		if(opposition.rating < rating +40){
			rating += 3;
			return;
		}
		if(opposition.rating < rating +50) {
			rating += 3;
			return;
		}
		if(opposition.rating < rating +60) {
			rating += 2;
			return;
		}
	}
	
	public void loss(Team opposition) {
		if(opposition.rating > rating +60) {
			rating -= 2;
			if(rating<0)
				rating=0;
			return;
		}
		if(opposition.rating > rating +40) {
			rating -= 3;
			if(rating<0)
				rating=0;
			return;
		}
		if(opposition.rating > rating +20) {
			rating -= 4;
			if(rating<0)
				rating=0;
			return;
		}
		if(opposition.rating < rating +10) {
			rating -= 5;
			if(rating<0)
				rating=0;
			return;
		}
		if(opposition.rating < rating +20) {
			rating -= 6;
			if(rating<0)
				rating=0;
			return;
		}
		if(opposition.rating < rating +30) {
			rating -= 7;
			if(rating<0)
				rating=0;
			return;
		}
		if(opposition.rating < rating +40) {
			rating -= 8;
			if(rating<0)
				rating=0;
			return;
		}
		if(opposition.rating < rating +50) {
			rating -= 9;
			if(rating<0)
				rating=0;
			return;
		}
		if(opposition.rating < rating +60) {
			rating -= 10;
			if(rating<0)
				rating=0;
			return;
		}
	}
	
	public void intlWin(Team opposition) {
		if(opposition.intlRating > intlRating +60) {
			intlRating += 15;
			return;
		}
		if(opposition.intlRating > intlRating +30) {
			intlRating += 10;
			return;
		}
		if(opposition.intlRating > intlRating +10) {
			intlRating += 9;
			return;
		}
		if(opposition.intlRating < intlRating +10)
		{
			intlRating += 8;
			return;
		}
		if(opposition.intlRating < intlRating +20) {
			intlRating += 7;
			return;
		}
		if(opposition.intlRating < intlRating +30) {
			intlRating += 6;
			return;
		}
		if(opposition.intlRating < intlRating +40){
			intlRating += 5;
			return;
		}
		if(opposition.intlRating < intlRating +50) {
			intlRating += 4;
			return;
		}
		if(opposition.intlRating < intlRating +60) {
			intlRating += 3;
			return;
		}
	}
	
	public void intlLoss(Team opposition) {
		if(opposition.intlRating > intlRating +60) {
			intlRating -= 2;
			if(intlRating<0)
				intlRating=0;
			return;
		}
		if(opposition.intlRating > intlRating +40) {
			intlRating -= 3;
			if(intlRating<0)
				intlRating=0;
			return;
		}
		if(opposition.intlRating > intlRating +20) {
			intlRating -= 4;
			if(intlRating<0)
				intlRating=0;
			return;
		}
		if(opposition.intlRating < intlRating +10) {
			intlRating -= 5;
			if(intlRating<0)
				intlRating=0;
			return;
		}
		if(opposition.intlRating < intlRating +20) {
			intlRating -= 6;
			if(intlRating<0)
				intlRating=0;
			return;
		}
		if(opposition.intlRating < intlRating +30) {
			intlRating -= 7;
			if(intlRating<0)
				intlRating=0;
			return;
		}
		if(opposition.intlRating < intlRating +40) {
			intlRating -= 8;
			if(intlRating<0)
				intlRating=0;
			return;
		}
		if(opposition.intlRating < intlRating +50) {
			intlRating -= 9;
			if(intlRating<0)
				intlRating=0;
			return;
		}
		if(opposition.intlRating < intlRating +60) {
			intlRating -= 10;
			if(intlRating<0)
				intlRating=0;
			return;
		}
	}
	
	public void finalWin() {
		rating += 12;
	}
	
	public void finalIntlWin() {
		rating += 20;
	}
	
	public void winRound() {
		score+=1;
	}
	
	public void resetScore() {
		score = 0;
	}
	
	public void resetMapsWon() {
		mapsWon = 0;
	}
	
	// Comparator for sorting the list by champsWins
    public static Comparator<Team> champsOrder = new Comparator<Team>() {
 
        // Method
        public int compare(Team t1, Team t2) {
 
            int champs1 = t1.getChampsWins();
            int champs2 = t2.getChampsWins();
 
            // For ascending order
            //return rating1 - rating2;
            
            // For descending order
            return champs2-champs1;
        }
    };
	
	// Comparator for sorting the list by rating
    public static Comparator<Team> ratingOrder = new Comparator<Team>() {
 
        // Method
        public int compare(Team t1, Team t2) {
 
            int rating1 = t1.getRating();
            int rating2 = t2.getRating();
 
            // For ascending order
            //return rating1 - rating2;
            
            // For descending order
            return rating2-rating1;
        }
    };
    
    // Comparator for sorting the list by international rating
    public static Comparator<Team> internationalRatingOrder = new Comparator<Team>() {
 
        // Method
        public int compare(Team t1, Team t2) {
 
            int rating1 = t1.getIntlRating();
            int rating2 = t2.getIntlRating();
 
            // For ascending order
            //return rating1 - rating2;
            
            // For descending order
            return rating2-rating1;
        }
    };
    
    // Comparator for sorting the list by games played
    public static Comparator<Team> gamesPlayedOrder = new Comparator<Team>() {
 
        // Method
        public int compare(Team t1, Team t2) {
 
            int rating1 = t1.getGamesPlayed();
            int rating2 = t2.getGamesPlayed();
 
            // For ascending order
            //return rating1 - rating2;
            
            // For descending order
            return rating2-rating1;
        }
    };    
    
    // Comparator for sorting the list by bindW
    public static Comparator<Team> bindWOrder = new Comparator<Team>() {
 
        // Method
        public int compare(Team t1, Team t2) {
 
            int rating1 = (int)t1.getBindW();
            int rating2 = (int)t2.getBindW();
 
            // For ascending order
            //return rating1 - rating2;
            
            // For descending order
            return rating2-rating1;
        }
    };
    
    // Comparator for sorting the list by bindAtt
    public static Comparator<Team> bindAttOrder = new Comparator<Team>() {
 
        // Method
        public int compare(Team t1, Team t2) {
 
            int rating1 = (int)t1.getBindAtt();
            int rating2 = (int)t2.getBindAtt();
 
            // For ascending order
            //return rating1 - rating2;
            
            // For descending order
            return rating2-rating1;
        }
    };

    // Comparator for sorting the list by bindDef
    public static Comparator<Team> bindDefOrder = new Comparator<Team>() {
 
        // Method
        public int compare(Team t1, Team t2) {
 
            int rating1 = (int)t1.getBindDef();
            int rating2 = (int)t2.getBindDef();
 
            // For ascending order
            //return rating1 - rating2;
            
            // For descending order
            return rating2-rating1;
        }
    };
    
    // Comparator for sorting the list by bindGames
    public static Comparator<Team> bindGamesOrder = new Comparator<Team>() {
 
        // Method
        public int compare(Team t1, Team t2) {
 
            int rating1 = (int)t1.getBindGames();
            int rating2 = (int)t2.getBindGames();
 
            // For ascending order
            //return rating1 - rating2;
            
            // For descending order
            return rating2-rating1;
        }
    };
    
    // Comparator for sorting the list by abyssW
    public static Comparator<Team> abyssWOrder = new Comparator<Team>() {
 
        // Method
        public int compare(Team t1, Team t2) {
 
            int rating1 = (int)t1.getAbyssW();
            int rating2 = (int)t2.getAbyssW();
 
            // For ascending order
            //return rating1 - rating2;
            
            // For descending order
            return rating2-rating1;
        }
    };

    // Comparator for sorting the list by abyssAtt
    public static Comparator<Team> abyssAttOrder = new Comparator<Team>() {
 
        // Method
        public int compare(Team t1, Team t2) {
 
            int rating1 = (int)t1.getAbyssAtt();
            int rating2 = (int)t2.getAbyssAtt();
 
            // For ascending order
            //return rating1 - rating2;
            
            // For descending order
            return rating2-rating1;
        }
    };

    // Comparator for sorting the list by abyssDef
    public static Comparator<Team> abyssDefOrder = new Comparator<Team>() {
 
        // Method
        public int compare(Team t1, Team t2) {
 
            int rating1 = (int)t1.getAbyssDef();
            int rating2 = (int)t2.getAbyssDef();
 
            // For ascending order
            //return rating1 - rating2;
            
            // For descending order
            return rating2-rating1;
        }
    };

    // Comparator for sorting the list by abyssGames
    public static Comparator<Team> abyssGamesOrder = new Comparator<Team>() {
 
        // Method
        public int compare(Team t1, Team t2) {
 
            int rating1 = (int)t1.getAbyssGames();
            int rating2 = (int)t2.getAbyssGames();
 
            // For ascending order
            //return rating1 - rating2;
            
            // For descending order
            return rating2-rating1;
        }
    };
    
    // Comparator for sorting the list by ascentW
    public static Comparator<Team> ascentWOrder = new Comparator<Team>() {
 
        // Method
        public int compare(Team t1, Team t2) {
 
            int rating1 = (int)t1.getAscentW();
            int rating2 = (int)t2.getAscentW();
 
            // For ascending order
            //return rating1 - rating2;
            
            // For descending order
            return rating2-rating1;
        }
    };
    
    // Comparator for sorting the list by ascentAtt
    public static Comparator<Team> ascentAttOrder = new Comparator<Team>() {
 
        // Method
        public int compare(Team t1, Team t2) {
 
            int rating1 = (int)t1.getAscentAtt();
            int rating2 = (int)t2.getAscentAtt();
 
            // For ascending order
            //return rating1 - rating2;
            
            // For descending order
            return rating2-rating1;
        }
    };
    
    // Comparator for sorting the list by ascentDef
    public static Comparator<Team> ascentDefOrder = new Comparator<Team>() {
 
        // Method
        public int compare(Team t1, Team t2) {
 
            int rating1 = (int)t1.getAscentDef();
            int rating2 = (int)t2.getAscentDef();
 
            // For ascending order
            //return rating1 - rating2;
            
            // For descending order
            return rating2-rating1;
        }
    };
    
    // Comparator for sorting the list by ascentGames
    public static Comparator<Team> ascentGamesOrder = new Comparator<Team>() {
 
        // Method
        public int compare(Team t1, Team t2) {
 
            int rating1 = (int)t1.getAscentGames();
            int rating2 = (int)t2.getAscentGames();
 
            // For ascending order
            //return rating1 - rating2;
            
            // For descending order
            return rating2-rating1;
        }
    };
    
    // Comparator for sorting the list by havenW
    public static Comparator<Team> havenWOrder = new Comparator<Team>() {
 
        // Method
        public int compare(Team t1, Team t2) {
 
            int rating1 = (int)t1.getHavenW();
            int rating2 = (int)t2.getHavenW();
 
            // For ascending order
            //return rating1 - rating2;
            
            // For descending order
            return rating2-rating1;
        }
    };
    
    // Comparator for sorting the list by havenAtt
    public static Comparator<Team> havenAttOrder = new Comparator<Team>() {
 
        // Method
        public int compare(Team t1, Team t2) {
 
            int rating1 = (int)t1.getHavenAtt();
            int rating2 = (int)t2.getHavenAtt();
 
            // For ascending order
            //return rating1 - rating2;
            
            // For descending order
            return rating2-rating1;
        }
    };
    
    // Comparator for sorting the list by havenDef
    public static Comparator<Team> havenDefOrder = new Comparator<Team>() {
 
        // Method
        public int compare(Team t1, Team t2) {
 
            int rating1 = (int)t1.getHavenDef();
            int rating2 = (int)t2.getHavenDef();
 
            // For ascending order
            //return rating1 - rating2;
            
            // For descending order
            return rating2-rating1;
        }
    };
    
    // Comparator for sorting the list by havenGames
    public static Comparator<Team> havenGamesOrder = new Comparator<Team>() {
 
        // Method
        public int compare(Team t1, Team t2) {
 
            int rating1 = (int)t1.getHavenGames();
            int rating2 = (int)t2.getHavenGames();
 
            // For ascending order
            //return rating1 - rating2;
            
            // For descending order
            return rating2-rating1;
        }
    };
    
    // Comparator for sorting the list by iceboxW
    public static Comparator<Team> iceboxWOrder = new Comparator<Team>() {
 
        // Method
        public int compare(Team t1, Team t2) {
 
            int rating1 = (int)t1.getIceboxW();
            int rating2 = (int)t2.getIceboxW();
 
            // For ascending order
            //return rating1 - rating2;
            
            // For descending order
            return rating2-rating1;
        }
    };
    
    // Comparator for sorting the list by iceboxAtt
    public static Comparator<Team> iceboxAttOrder = new Comparator<Team>() {
 
        // Method
        public int compare(Team t1, Team t2) {
 
            int rating1 = (int)t1.getIceboxAtt();
            int rating2 = (int)t2.getIceboxAtt();
 
            // For ascending order
            //return rating1 - rating2;
            
            // For descending order
            return rating2-rating1;
        }
    };

    // Comparator for sorting the list by iceboxDef
    public static Comparator<Team> iceboxDefOrder = new Comparator<Team>() {
 
        // Method
        public int compare(Team t1, Team t2) {
 
            int rating1 = (int)t1.getIceboxDef();
            int rating2 = (int)t2.getIceboxDef();
 
            // For ascending order
            //return rating1 - rating2;
            
            // For descending order
            return rating2-rating1;
        }
    };
    
    // Comparator for sorting the list by iceboxGames
    public static Comparator<Team> iceboxGamesOrder = new Comparator<Team>() {
 
        // Method
        public int compare(Team t1, Team t2) {
 
            int rating1 = (int)t1.getIceboxGames();
            int rating2 = (int)t2.getIceboxGames();
 
            // For ascending order
            //return rating1 - rating2;
            
            // For descending order
            return rating2-rating1;
        }
    };
    
    // Comparator for sorting the list by lotusW
    public static Comparator<Team> lotusWOrder = new Comparator<Team>() {
 
        // Method
        public int compare(Team t1, Team t2) {
 
            int rating1 = (int)t1.getLotusW();
            int rating2 = (int)t2.getLotusW();
 
            // For ascending order
            //return rating1 - rating2;
            
            // For descending order
            return rating2-rating1;
        }
    };

    // Comparator for sorting the list by lotusAtt
    public static Comparator<Team> lotusAttOrder = new Comparator<Team>() {
 
        // Method
        public int compare(Team t1, Team t2) {
 
            int rating1 = (int)t1.getLotusAtt();
            int rating2 = (int)t2.getLotusAtt();
 
            // For ascending order
            //return rating1 - rating2;
            
            // For descending order
            return rating2-rating1;
        }
    };

    // Comparator for sorting the list by lotusDef
    public static Comparator<Team> lotusDefOrder = new Comparator<Team>() {
 
        // Method
        public int compare(Team t1, Team t2) {
 
            int rating1 = (int)t1.getLotusDef();
            int rating2 = (int)t2.getLotusDef();
 
            // For ascending order
            //return rating1 - rating2;
            
            // For descending order
            return rating2-rating1;
        }
    };
    
    // Comparator for sorting the list by lotusGames
    public static Comparator<Team> lotusGamesOrder = new Comparator<Team>() {
 
        // Method
        public int compare(Team t1, Team t2) {
 
            int rating1 = (int)t1.getLotusGames();
            int rating2 = (int)t2.getLotusGames();
 
            // For ascending order
            //return rating1 - rating2;
            
            // For descending order
            return rating2-rating1;
        }
    };
    
    // Comparator for sorting the list by sunsetW
    public static Comparator<Team> sunsetWOrder = new Comparator<Team>() {
 
        // Method
        public int compare(Team t1, Team t2) {
 
            int rating1 = (int)t1.getSunsetW();
            int rating2 = (int)t2.getSunsetW();
 
            // For ascending order
            //return rating1 - rating2;
            
            // For descending order
            return rating2-rating1;
        }
    };
    
    // Comparator for sorting the list by sunsetAtt
    public static Comparator<Team> sunsetAttOrder = new Comparator<Team>() {
 
        // Method
        public int compare(Team t1, Team t2) {
 
            int rating1 = (int)t1.getSunsetAtt();
            int rating2 = (int)t2.getSunsetAtt();
 
            // For ascending order
            //return rating1 - rating2;
            
            // For descending order
            return rating2-rating1;
        }
    };
    
    // Comparator for sorting the list by sunsetDef
    public static Comparator<Team> sunsetDefOrder = new Comparator<Team>() {
 
        // Method
        public int compare(Team t1, Team t2) {
 
            int rating1 = (int)t1.getSunsetDef();
            int rating2 = (int)t2.getSunsetDef();
 
            // For ascending order
            //return rating1 - rating2;
            
            // For descending order
            return rating2-rating1;
        }
    };
	
    // Comparator for sorting the list by bindGames
    public static Comparator<Team> sunsetGamesOrder = new Comparator<Team>() {
 
        // Method
        public int compare(Team t1, Team t2) {
 
            int rating1 = (int)t1.getSunsetGames();
            int rating2 = (int)t2.getSunsetGames();
 
            // For ascending order
            //return rating1 - rating2;
            
            // For descending order
            return rating2-rating1;
        }
    };
    
	//setters and getters
	public String getName() {
		return name;
	}

	public void setName(String newName) {
		this.name = newName;
	}
	
    public int getSeed(){
        return seed;
    }

    public void setSeed(int seed){
        this.seed = seed;
    }

	public int getChampsWins() {
		return champsWon;
	}
	
	public int getRating() {
		return rating;
	}
	
	public int getIntlRating() {
		return intlRating;
	}
	
	public String getRegion() {
		return region;
	}
	
	public int getGamesPlayed() {
		return gamesPlayed;
	}
	
	public void setGamesPlayed(int gamesPlayed) {
		this.gamesPlayed = gamesPlayed;
	}
	
	public float getAbyssW() {
		return abyssW;
	}
	
	public float getAbyssAtt() {
		return abyssAtt;
	}
	
	public float getAbyssDef() {
		return abyssDef;
	}
	
	public float getAbyssGames() {
		return abyssDefSel+abyssAttSel;
	}
	
	public float getAscentW() {
		return ascentW;
	}
	
	public float getAscentAtt() {
		return ascentAtt;
	}
	
	public float getAscentDef() {
		return ascentDef;
	}
	
	public float getAscentGames() {
		return ascentDefSel+ascentAttSel;
	}
	
	public float getBindW() {
		return bindW;
	}
	
	public float getBindAtt() {
		return bindAtt;
	}

	public float getBindDef() {
		return bindDef;
	}
	
	public float getBindGames() {
		return bindDefSel+bindAttSel;
	}
	
	public float getHavenW() {
		return havenW;
	}
	
	public float getHavenAtt() {
		return havenAtt;
	}
	
	public float getHavenDef() {
		return havenDef;
	}
	
	public float getHavenGames() {
		return havenDefSel+havenAttSel;
	}
	
	public float getIceboxW() {
		return iceboxW;
	}
	
	public float getIceboxAtt() {
		return iceboxAtt;
	}
	
	public float getIceboxDef() {
		return iceboxDef;
	}
	
	public float getIceboxGames() {
		return iceboxDefSel+iceboxAttSel;
	}
	
	public float getLotusW() {
		return lotusW;
	}
	
	public float getLotusAtt() {
		return lotusAtt;
	}

	public float getLotusDef() {
		return lotusDef;
	}
	
	public float getLotusGames() {
		return lotusDefSel+lotusAttSel;
	}
	
	public float getSunsetW() {
		return sunsetW;
	}
	
	public float getSunsetAtt() {
		return sunsetAtt;
	}
	
	public float getSunsetDef() {
		return sunsetDef;
	}
	
	public float getSunsetGames() {
		return sunsetDefSel+sunsetAttSel;
	}
	
	public float[] mapDetails(String map) {
		float[] teamMapStats = new float[3];
		if(map.equalsIgnoreCase("Abyss")) {
			teamMapStats[0] = getAbyssW()/100;
			teamMapStats[1] = getAbyssAtt()/100;
			teamMapStats[2] = getAbyssDef()/100;
		}
		else if(map.equalsIgnoreCase("Ascent")) {
			teamMapStats[0] = getAscentW()/100;
			teamMapStats[1] = getAscentAtt()/100;
			teamMapStats[2] = getAscentDef()/100;
		}
		else if(map.equalsIgnoreCase("Bind")) {
			teamMapStats[0] = getBindW()/100;
			teamMapStats[1] = getBindAtt()/100;
			teamMapStats[2] = getBindDef()/100;
		}
		else if(map.equalsIgnoreCase("Haven")) {
			teamMapStats[0] = getHavenW()/100;
			teamMapStats[1] = getHavenAtt()/100;
			teamMapStats[2] = getHavenDef()/100;
		}
		else if(map.equalsIgnoreCase("Icebox")) {
			teamMapStats[0] = getIceboxW()/100;
			teamMapStats[1] = getIceboxAtt()/100;
			teamMapStats[2] = getIceboxDef()/100;
		}
		else if(map.equalsIgnoreCase("Lotus")) {
			teamMapStats[0] = getLotusW()/100;
			teamMapStats[1] = getLotusAtt()/100;
			teamMapStats[2] = getLotusDef()/100;
		}
		else if(map.equalsIgnoreCase("Sunset")) {
			teamMapStats[0] = getSunsetW()/100;
			teamMapStats[1] = getSunsetAtt()/100;
			teamMapStats[2] = getSunsetDef()/100;
		}
		return teamMapStats;
	}
}
