package com.kingmang.lazurite.libraries.lzr.utils.artify.styles.impl

import com.kingmang.lazurite.libraries.lzr.utils.artify.styles.IArtifyStyle

class DoomStyle : IArtifyStyle {
    override val styleName: String
        get() = "doom"

    override fun getArtMap(): Map<Char, String> {
        return mapOf(
            'B' to """
                ______ 
                | ___ \
                | |_/ /
                | ___ \
                | |_/ /
                \____/ 
                       
                       
            """.trimIndent(),

            'A' to """
                  ___  
                 / _ \ 
                / /_\ \
                |  _  |
                | | | |
                \_| |_/
                       
                       
            """.trimIndent(),

            'C' to """
                 _____ 
                /  __ \
                | /  \/
                | |    
                | \__/\
                 \____/
                       
                       
            """.trimIndent(),

            'E' to """
                 _____ 
                |  ___|
                | |__  
                |  __| 
                | |___ 
                \____/ 
                       
                       
            """.trimIndent(),

            'D' to """
                ______ 
                |  _  \
                | | | |
                | | | |
                | |/ / 
                |___/  
                       
                       
            """.trimIndent(),

            'F' to """
                ______ 
                |  ___|
                | |_   
                |  _|  
                | |    
                \_|    
                       
                       
            """.trimIndent(),

            'G' to """
                 _____ 
                |  __ \
                | |  \/
                | | __ 
                | |_\ \
                 \____/
                       
                       
            """.trimIndent(),

            'H' to """
                 _   _ 
                | | | |
                | |_| |
                |  _  |
                | | | |
                \_| |_/
                       
                       
            """.trimIndent(),

            'I' to """
                 _____ 
                |_   _|
                  | |  
                  | |  
                 _| |_ 
                 \___/ 
                       
                       
            """.trimIndent(),

            'J' to """
                   ___ 
                  |_  |
                    | |
                    | |
                /\__/ /
                \____/ 
                       
                       
            """.trimIndent(),

            'K' to """
                 _   __
                | | / /
                | |/ / 
                |    \ 
                | |\  \
                \_| \_/
                       
                       
            """.trimIndent(),

            'L' to """
                 _     
                | |    
                | |    
                | |    
                | |____
                \_____/
                       
                       
            """.trimIndent(),

            'M' to """
                ___  ___
                |  \/  |
                | .  . |
                | |\/| |
                | |  | |
                \_|  |_/
                        
                        
            """.trimIndent(),

            'O' to """
                 _____ 
                |  _  |
                | | | |
                | | | |
                \ \_/ /
                 \___/ 
                       
                       
            """.trimIndent(),

            'N' to """
                 _   _ 
                | \ | |
                |  \| |
                | . ` |
                | |\  |
                \_| \_/
                       
                       
            """.trimIndent(),

            'Q' to """
                 _____ 
                |  _  |
                | | | |
                | | | |
                \ \/' /
                 \_/\_\
                       
                       
            """.trimIndent(),

            'P' to """
                ______ 
                | ___ \
                | |_/ /
                |  __/ 
                | |    
                \_|    
                       
                       
            """.trimIndent(),

            'R' to """
                ______ 
                | ___ \
                | |_/ /
                |    / 
                | |\ \ 
                \_| \_|
                       
                       
            """.trimIndent(),

            'T' to """
                 _____ 
                |_   _|
                  | |  
                  | |  
                  | |  
                  \_/  
                       
                       
            """.trimIndent(),

            'S' to """
                 _____ 
                /  ___|
                \ `--. 
                 `--. \
                /\__/ /
                \____/ 
                       
                       
            """.trimIndent(),

            'U' to """
                 _   _ 
                | | | |
                | | | |
                | | | |
                | |_| |
                 \___/ 
                       
                       
            """.trimIndent(),

            'W' to """
                 _    _ 
                | |  | |
                | |  | |
                | |/\| |
                \  /\  /
                 \/  \/ 
                        
                        
            """.trimIndent(),

            'V' to """
                 _   _ 
                | | | |
                | | | |
                | | | |
                \ \_/ /
                 \___/ 
                       
                       
            """.trimIndent(),

            'X' to """
                __   __
                \ \ / /
                 \ V / 
                 /   \ 
                / /^\ \
                \/   \/
                       
                       
            """.trimIndent(),

            'Y' to """
                __   __
                \ \ / /
                 \ V / 
                  \ /  
                  | |  
                  \_/  
                       
                       
            """.trimIndent(),

            'Z' to """
                 ______
                |___  /
                   / / 
                  / /  
                ./ /___
                \_____/
                       
                       
            """.trimIndent(),

            'a' to """
                       
                       
                  __ _ 
                 / _` |
                | (_| |
                 \__,_|
                       
                       
            """.trimIndent(),

            'b' to """
                 _     
                | |    
                | |__  
                | '_ \ 
                | |_) |
                |_.__/ 
                       
                       
            """.trimIndent(),

            'd' to """
                     _ 
                    | |
                  __| |
                 / _` |
                | (_| |
                 \__,_|
                       
                       
            """.trimIndent(),

            'c' to """
                      
                      
                  ___ 
                 / __|
                | (__ 
                 \___|
                      
                      
            """.trimIndent(),

            'e' to """
                      
                      
                  ___ 
                 / _ \
                |  __/
                 \___|
                      
                      
            """.trimIndent(),

            'g' to """
                       
                       
                  __ _ 
                 / _` |
                | (_| |
                 \__, |
                  __/ |
                 |___/ 
            """.trimIndent(),

            'f' to """
                  __ 
                 / _|
                | |_ 
                |  _|
                | |  
                |_|  
                     
                     
            """.trimIndent(),

            'h' to """
                 _     
                | |    
                | |__  
                | '_ \ 
                | | | |
                |_| |_|
                       
                       
            """.trimIndent(),

            'i' to """
                 _ 
                (_)
                 _ 
                | |
                | |
                |_|
                   
                   
            """.trimIndent(),

            'j' to """
                   _ 
                  (_)
                   _ 
                  | |
                  | |
                  | |
                 _/ |
                |__/ 
            """.trimIndent(),

            'k' to """
                 _    
                | |   
                | | __
                | |/ /
                |   < 
                |_|\_\
                      
                      
            """.trimIndent(),

            'm' to """
                           
                           
                 _ __ ___  
                | '_ ` _ \ 
                | | | | | |
                |_| |_| |_|
                           
                           
            """.trimIndent(),

            'l' to """
                 _ 
                | |
                | |
                | |
                | |
                |_|
                   
                   
            """.trimIndent(),

            'n' to """
                       
                       
                 _ __  
                | '_ \ 
                | | | |
                |_| |_|
                       
                       
            """.trimIndent(),

            'o' to """
                       
                       
                  ___  
                 / _ \ 
                | (_) |
                 \___/ 
                       
                       
            """.trimIndent(),

            'p' to """
                       
                       
                 _ __  
                | '_ \ 
                | |_) |
                | .__/ 
                | |    
                |_|    
            """.trimIndent(),

            'q' to """
                       
                       
                  __ _ 
                 / _` |
                | (_| |
                 \__, |
                    | |
                    |_|
            """.trimIndent(),

            's' to """
                     
                     
                 ___ 
                / __|
                \__ \
                |___/
                     
                     
            """.trimIndent(),

            'r' to """
                      
                      
                 _ __ 
                | '__|
                | |   
                |_|   
                      
                      
            """.trimIndent(),

            't' to """
                 _   
                | |  
                | |_ 
                | __|
                | |_ 
                 \__|
                     
                     
            """.trimIndent(),

            'v' to """
                       
                       
                __   __
                \ \ / /
                 \ V / 
                  \_/  
                       
                       
            """.trimIndent(),

            'u' to """
                       
                       
                 _   _ 
                | | | |
                | |_| |
                 \__,_|
                       
                       
            """.trimIndent(),

            'w' to """
                          
                          
                __      __
                \ \ /\ / /
                 \ V  V / 
                  \_/\_/  
                          
                          
            """.trimIndent(),

            'x' to """
                      
                      
                __  __
                \ \/ /
                 >  < 
                /_/\_\
                      
                      
            """.trimIndent(),

            'y' to """
                       
                       
                 _   _ 
                | | | |
                | |_| |
                 \__, |
                  __/ |
                 |___/ 
            """.trimIndent(),

            'z' to """
                     
                     
                 ____
                |_  /
                 / / 
                /___|
                     
                     
            """.trimIndent(),

            ' ' to """
                 
                 
                 
                 
                 
                 
                 
                 
            """.trimIndent(),

            '@' to """
                         
                   ____  
                  / __ \ 
                 / / _` |
                | | (_| |
                 \ \__,_|
                  \____/ 
                         
            """.trimIndent(),

            '#' to """
                   _  _   
                 _| || |_ 
                |_  __  _|
                 _| || |_ 
                |_  __  _|
                  |_||_|  
                          
                          
            """.trimIndent(),

            '%' to """
                 _   __
                (_) / /
                   / / 
                  / /  
                 / / _ 
                /_/ (_)
                       
                       
            """.trimIndent(),

            '$' to """
                  _  
                 | | 
                / __)
                \__ \
                (   /
                 |_| 
                     
                     
            """.trimIndent(),

            '^' to """
                 /\ 
                |/\|
                    
                    
                    
                    
                    
                    
            """.trimIndent(),

            '&' to """
                        
                  ___   
                 ( _ )  
                 / _ \/\
                | (_>  <
                 \___/\/
                        
                        
            """.trimIndent(),

            '*' to """
                    _    
                 /\| |/\ 
                 \ ` ' / 
                |_     _|
                 / , . \ 
                 \/|_|\/ 
                         
                         
            """.trimIndent(),

            '(' to """
                  __
                 / /
                | | 
                | | 
                | | 
                | | 
                 \_\
                    
            """.trimIndent(),

            '1' to """
                 __  
                /  | 
                `| | 
                 | | 
                _| |_
                \___/
                     
                     
            """.trimIndent(),

            ')' to """
                __  
                \ \ 
                 | |
                 | |
                 | |
                 | |
                /_/ 
                    
            """.trimIndent(),

            '2' to """
                 _____ 
                / __  \
                `' / /'
                  / /  
                ./ /___
                \_____/
                       
                       
            """.trimIndent(),

            '3' to """
                 _____ 
                |____ |
                    / /
                    \ \
                .___/ /
                \____/ 
                       
                       
            """.trimIndent(),

            '4' to """
                   ___ 
                  /   |
                 / /| |
                / /_| |
                \___  |
                    |_/
                       
                       
            """.trimIndent(),

            '5' to """
                 _____ 
                |  ___|
                |___ \ 
                    \ \
                /\__/ /
                \____/ 
                       
                       
            """.trimIndent(),

            '7' to """
                 ______
                |___  /
                   / / 
                  / /  
                ./ /   
                \_/    
                       
                       
            """.trimIndent(),

            '6' to """
                  ____ 
                 / ___|
                / /___ 
                | ___ \
                | \_/ |
                \_____/
                       
                       
            """.trimIndent(),

            '8' to """
                 _____ 
                |  _  |
                 \ V / 
                 / _ \ 
                | |_| |
                \_____/
                       
                       
            """.trimIndent(),

            '0' to """
                 _____ 
                |  _  |
                | |/' |
                |  /| |
                \ |_/ /
                 \___/ 
                       
                       
            """.trimIndent(),

            '9' to """
                 _____ 
                |  _  |
                | |_| |
                \____ |
                .___/ /
                \____/ 
                       
                       
            """.trimIndent()
        )
    }
}
